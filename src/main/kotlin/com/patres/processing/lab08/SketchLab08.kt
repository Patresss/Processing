package com.patres.processing.lab08

import com.patres.processing.fill
import com.patres.processing.lab02.model.BallModel
import org.jbox2d.dynamics.contacts.Contact
import processing.core.PApplet
import processing.core.PConstants
import shiffman.box2d.Box2DProcessing
import java.awt.Color

class SketchLab08 : PApplet() {

    companion object {
        val SIZE_X = 1920
        val SIZE_Y = 1080
        val DEBUG_MODE = false
    }

    lateinit var board: Board

    override fun settings() {
        size(SketchLab08.SIZE_X, SketchLab08.SIZE_Y)
        board = Board(this)
    }

    override fun setup() {
    }

    override fun draw() {
        background(255f)
        board.draw()
        if (DEBUG_MODE) {
            drawInformation()
        }
    }

    override fun keyPressed() {
        when (keyCode) {
        }
    }

    private fun drawInformation() {
        fill(Color.WHITE)
        textSize(50f)

        if (DEBUG_MODE) {
            pushStyle()
            fill(Color.BLUE)
            textSize(10f)
            textAlign(PConstants.LEFT)

            text("Klatki         : $frameRate", 10f, 20f)
            popStyle()
        }
    }

    override fun mousePressed() {
    }

    override fun mouseReleased() {
    }

}