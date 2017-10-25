package com.patres.processing.lab03.model

import com.patres.processing.fill
import com.patres.processing.lab03.PongGame
import java.awt.Color

class Board(
        val pongGame: PongGame,
        var sizeX: Int,
        var sizeY: Int,
        var color: Color = Color.WHITE,
        var borderWidth: Float = 10f
) {

    fun draw() {
        pongGame.pApplet.fill(color)
        pongGame.pApplet.rect(0f, 0f, sizeX.toFloat(), borderWidth) // up
        pongGame.pApplet.rect(0f, sizeY - borderWidth, sizeX.toFloat(), sizeY.toFloat()) // down
        pongGame.pApplet.rect(0f, 0f, borderWidth, sizeY.toFloat()) // left
        pongGame.pApplet.rect(sizeX - borderWidth, 0f, sizeX.toFloat(), sizeY.toFloat()) // right
    }
}