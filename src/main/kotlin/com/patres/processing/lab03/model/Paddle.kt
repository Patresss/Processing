package com.patres.processing.lab03.model

import com.patres.processing.lab03.Player
import com.patres.processing.lab03.PongGame
import processing.core.PVector

class Paddle(
        val player: Player,
        val pongGame: PongGame,
        var width: Float = 200f,
        private var height: Float = 10f,
        private var margin: Float = 50f,
        var subPoints: ArrayList<PVector> = ArrayList()
) {

    private var vectorLeft = PVector()
    private var vectorRight = PVector()
    private val paddleImage = pongGame.pApplet.loadImage("img/ufo.jpg")!!

    private var position: Float = pongGame.board.sizeX.toFloat() / 2 - width / 2
        set(value) {
            val minPositionX = pongGame.board.borderWidth
            val maxPositionX = pongGame.board.sizeX.toFloat() - pongGame.board.borderWidth - width
            field = when {
                value < minPositionX -> minPositionX
                value > maxPositionX -> maxPositionX
                else -> value
            }
        }

    init {
        updateVectors()
    }

    fun getNormalizedVector(): PVector {
        val baseDelta = PVector.sub(vectorLeft, vectorRight)
        baseDelta.normalize()
        return PVector(-baseDelta.y, baseDelta.x)
    }

    fun draw() {
        pongGame.pApplet.image(paddleImage, vectorLeft.x, vectorLeft.y, width, height)
        fillCoordinatePoints()
    }

    fun movePosition(value: Float) {
        position = value
        updateVectors()
    }

    fun move(value: Float) {
        position += value
        updateVectors()
    }

    private fun updateVectors() {
        val leftPositionX = position
        val rightPositionX = width + position

        if (player.up) {
            val positionY = margin
            vectorLeft = PVector(leftPositionX, positionY)
            vectorRight = PVector(rightPositionX, positionY)
        } else {
            val positionY = pongGame.board.sizeY - margin - height
            vectorLeft = PVector(leftPositionX, positionY)
            vectorRight = PVector(rightPositionX, positionY)
        }

    }

    private fun fillCoordinatePoints() {
        subPoints = ArrayList()
        val baseLength = PVector.dist(vectorLeft, vectorRight)
        for (i in 0 until width.toInt()) {
            val vector = PVector()
            vector.x = vectorLeft.x + (vectorRight.x - vectorLeft.x) / baseLength * i
            vector.y = vectorLeft.y + (vectorRight.y - vectorLeft.y) / baseLength * i
            if (player.up) vector.y += height
            subPoints.add(vector)
        }
    }


}