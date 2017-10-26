package com.patres.processing.lab03.model

import com.patres.processing.lab03.PongGame
import com.patres.processing.lab03.Position
import processing.core.PFont

class Player(
        var pongGame: PongGame,
        var numberOfLife: Int = 3,
        var paddleSpeed: Float,
        var up: Boolean,
        var name: String,
        var computer: Boolean = false
) {

    var paddleModel: PaddleModel = PaddleModel(player = this, pongGame = pongGame)


    fun draw() {
        if (computer) computerMove()
        paddleModel.draw()
    }

    fun move(position: Position) {
        when (position) {
            Position.LEFT -> paddleModel.move(position.value * paddleSpeed)
            Position.RIGHT -> paddleModel.move(position.value * paddleSpeed)
        }
    }

    fun changeMousePosition(value: Int) {
        paddleModel.movePosition(value + pongGame.board.borderWidth - paddleModel.width / 2)
    }

    fun computerMove() {
        val sortedBalls = pongGame.balls.sortedBy { it.position.y }
        val nearestBall = if (up) {
            sortedBalls.first()
        } else {
            sortedBalls.last()
        }
        paddleModel.movePosition(nearestBall.position.x + pongGame.board.borderWidth - paddleModel.width / 2)
    }


    fun lostLife() {
        pongGame.pause = true
        numberOfLife--
        pongGame.balls.forEach { it.newBallProperties() }
    }
}