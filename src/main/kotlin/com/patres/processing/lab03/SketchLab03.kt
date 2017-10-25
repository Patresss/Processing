package com.patres.processing.lab03

import processing.core.PApplet
import processing.core.PConstants

class SketchLab03 : PApplet() {

    companion object {
        val SIZE_X = 500
        val SIZE_Y = 800
        val BORDER_SIZE = 10f
        val PADDLE_SPEED = 10f
        val NUMBER_OF_BALLS = 2
    }

    private lateinit var pongGame: PongGame



    override fun settings() {
        size(SIZE_X, SIZE_Y)
    }

    override fun setup() {
        newGame()
    }

    override fun draw() {
        background(0f, 0f, 0f)
        pongGame.draw()
    }

    override fun keyPressed() {
        when (keyCode) {
            PConstants.LEFT -> pongGame.player1.move(Position.LEFT)
            PConstants.RIGHT -> pongGame.player1.move(Position.RIGHT)
        }

        when (key) {
            'a' -> pongGame.player2.move(Position.LEFT)
            's' -> pongGame.player2.move(Position.RIGHT)
        }

        if (key == ' ') {
            pongGame.pause = !pongGame.pause
            if(pongGame.player1.numberOfLife < 0 || pongGame.player2.numberOfLife < 0) {
                newGame()
            }
        }
    }

    fun newGame() {
        pongGame = PongGame(pApplet = this, sizeX = SIZE_X, sizeY = SIZE_Y, paddleSpeed = PADDLE_SPEED, numberOfBalls = NUMBER_OF_BALLS)
    }

}