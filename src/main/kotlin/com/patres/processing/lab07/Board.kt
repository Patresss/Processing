package com.patres.processing.lab07

import processing.core.PApplet
import shiffman.box2d.Box2DProcessing

class Board(
        val pApplet: PApplet,
        val box2d: Box2DProcessing
) {

    val imageKeeper = ImageKeeper(pApplet)
    val cannon = Cannon(board = this)
    val image = imageKeeper.background

    fun draw() {
        pApplet.image(image, 0f, 0f)
        cannon.draw()
    }
}