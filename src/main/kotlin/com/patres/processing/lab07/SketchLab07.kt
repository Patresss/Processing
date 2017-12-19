package com.patres.processing.lab07

import com.patres.processing.fill
import org.jbox2d.dynamics.BodyDef
import processing.core.PApplet
import processing.core.PConstants
import java.awt.Color
import shiffman.box2d.Box2DProcessing




class SketchLab07 : PApplet() {

    companion object {
        val SIZE_X = 1920
        val SIZE_Y = 1080
    }

    lateinit var board: Board
    lateinit var box2d: Box2DProcessing

    var startPressedTime: Long? = null
    var currentPower: Long = 0

    override fun settings() {
        size(SketchLab07.SIZE_X, SketchLab07.SIZE_Y)
    }

    override fun setup() {
        background(255)
        box2d = Box2DProcessing(this).apply {
            createWorld()
            setGravity(0f, -50f)
        }
        board = Board(pApplet = this, box2d = box2d)
    }

    override fun draw() {
        startPressedTime?.let { time ->
            currentPower = System.currentTimeMillis() - time
        }

        background(255)
        box2d.step()
        board.draw()

        drawInformation()
    }

    override fun keyPressed() {
        when (keyCode) {
            PConstants.LEFT -> board.cannon.changeAngle(false)
            PConstants.RIGHT -> board.cannon.changeAngle(true)
        }
    }

    private fun drawInformation() {
        fill(Color.BLUE)
        text("Frame           : $frameRate", 10f, 20f)
        text("currentPower    : $currentPower", 10f, 40f)
    }

    override fun mousePressed() {
        startPressedTime = System.currentTimeMillis()
    }

    override fun mouseReleased() {
        board.cannon.shot(currentPower)
        currentPower = 0
        startPressedTime = null
    }

}