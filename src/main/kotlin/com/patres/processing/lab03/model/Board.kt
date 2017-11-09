package com.patres.processing.lab03.model

import com.patres.processing.lab03.PongGame

class Board(
        val pongGame: PongGame,
        var sizeX: Int,
        var sizeY: Int,
        var borderWidth: Float = 10f
) {
    private val background = pongGame.pApplet.loadImage("img/lab03/dark-starry.jpg")!!
    private val borderImage = pongGame.pApplet.loadImage("img/lab03/wall.jpg")!!

    fun draw() {
        pongGame.pApplet.image(background, 0f, 0f, sizeX.toFloat(), sizeY.toFloat())
        pongGame.pApplet.image(borderImage, 0f, 0f, borderWidth, sizeY.toFloat()) // left
        pongGame.pApplet.image(borderImage, sizeX - borderWidth, 0f, sizeX.toFloat(), sizeY.toFloat()) // right
    }
}