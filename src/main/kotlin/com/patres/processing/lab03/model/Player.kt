package com.patres.processing.lab03.model

import com.patres.processing.lab03.PongGame
import com.patres.processing.lab03.Position

class Player(
        var pongGame: PongGame,
        var numberOfLife: Int = 3,
        var paddleSpeed: Float,
        var up: Boolean,
        var name: String
) {

    var paddleModel: PaddleModel = PaddleModel(player = this)

    fun draw() {
        paddleModel.draw()
    }

    fun move(position: Position) {
        when (position) {
            Position.LEFT -> paddleModel.move(position.value * paddleSpeed)
            Position.RIGHT -> paddleModel.move(position.value * paddleSpeed)
        }
    }

    fun lostLife() {
        pongGame.pause = true
        numberOfLife--
        pongGame.balls.forEach { it.newBallProperties() }
    }
}