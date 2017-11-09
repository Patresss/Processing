package com.patres.processing.lab03.model

import com.patres.processing.getPositiveOrNegativeValue
import com.patres.processing.lab03.PongGame
import processing.core.PVector


class PingBall(
        val pongGame: PongGame,
        private var radius: Float = 10.0f,
        private var speed: Float = 10.0f,
        private var velocity: PVector = PVector.random2D(),
        private var increaseSpeedStep: Float = 0.01f,
        var position: PVector = PVector()
) {

    private val ballImage = pongGame.pApplet.loadImage("img/lab03/sun-texture.jpg")!!

    init {
        newBallProperties()
    }

    fun newBallProperties() {
        velocity = PVector.random2D()
        velocity.x = -Math.abs(velocity.x)
        velocity.y = -Math.abs(velocity.y)
        velocity.mult(speed)
        position = PVector(pongGame.board.sizeX / 2f, pongGame.board.sizeY / 2f)
    }

    fun draw() {
        if (!pongGame.pause) {
            move()
            increaseSpeed(increaseSpeedStep)
            detectPaddle(pongGame.player1.paddleModel)
            detectPaddle(pongGame.player2.paddleModel)
        }
        drawBall()
    }

    private fun drawBall() {
        val mask = pongGame.pApplet.createGraphics(ballImage.width, ballImage.height)
        mask.beginDraw()
        mask.ellipse(position.x, position.y, radius * 2, radius * 2)
        mask.endDraw()
        ballImage.mask(mask)
        pongGame.pApplet.image(ballImage, 10f, 0f)
    }

    private fun detectPaddle(paddle: Paddle) {
        val normalizeVector = paddle.getNormalizedVector()

        val incidence = PVector.mult(velocity, -1f)
        incidence.normalize()
        paddle.subPoints.forEach{
            if(PVector.dist(position, it) < radius) {
                val dot = incidence.dot(normalizeVector)
                velocity.set( 2* normalizeVector.x * dot - incidence.x, 2 * normalizeVector.y * dot - incidence.y)
                velocity.mult(speed)
            }
        }
    }

    private fun move() {
        val borderWidth = pongGame.board.borderWidth
        position.x += velocity.x
        position.y += velocity.y


        if (position.x - radius < borderWidth) {
            velocity.x *= -1
        } else if (position.x + radius > pongGame.board.sizeX - borderWidth) {
            velocity.x *= -1
        }

        if (position.y - radius < borderWidth) {
            pongGame.player1.lostLife()
        } else if (position.y + radius > pongGame.board.sizeY - borderWidth) {
            pongGame.player2.lostLife()
        }
    }

    private fun increaseSpeed(value: Float) {
        speed += value
        val newValueX = velocity.x.getPositiveOrNegativeValue() * value
        velocity.x += newValueX
        val newValueY = velocity.y.getPositiveOrNegativeValue() * value
        velocity.y += newValueY
    }

}