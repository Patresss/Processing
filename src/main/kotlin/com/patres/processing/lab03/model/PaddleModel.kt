package com.patres.processing.lab03.model

import com.patres.processing.fill
import processing.core.PVector
import java.awt.Color

class PaddleModel(
        val player: Player,
        var color: Color = Color.WHITE,
        var width: Float = 200f,
        var height: Float = 10f,
        var margin: Float = 50f,
        var radii: Float = 5f,
        var subpoints: ArrayList<PVector> = ArrayList()
) {

    private var vectorLeft = PVector()
    private var vectorRight = PVector()

    var position: Float  = player.pongGame.board.sizeX.toFloat()/2 - width/2
        set(value) {
            val minPositionX = player.pongGame.board.borderWidth
            val maxPositionX = player.pongGame.board.sizeX.toFloat() - player.pongGame.board.borderWidth - width
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
        player.pongGame.pApplet.fill(color)
        player.pongGame.pApplet.rect(vectorLeft.x, vectorLeft.y, width, height, radii)
        fillCoordinatePoints()
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
            val positionY = player.pongGame.board.sizeY - margin - height
            vectorLeft = PVector(leftPositionX, positionY)
            vectorRight = PVector(rightPositionX, positionY)
        }

    }


    private fun fillCoordinatePoints() {
        subpoints = ArrayList()
        val baseLength = PVector.dist(vectorLeft, vectorRight)
        for (i in 0 until width.toInt()) {
            val vector = PVector()
            vector.x = vectorLeft.x + (vectorRight.x - vectorLeft.x) / baseLength * i
            vector.y = vectorLeft.y + (vectorRight.y - vectorLeft.y) / baseLength * i
            subpoints.add(vector)
        }
    }


}