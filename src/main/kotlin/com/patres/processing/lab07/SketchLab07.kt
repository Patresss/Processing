package com.patres.processing.lab07

import com.patres.processing.fill
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.contacts.Contact
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
        box2d.listenForCollisions()
        board = Board(pApplet = this, box2d = box2d)

    }

    override fun draw() {
        startPressedTime?.let { time ->
            currentPower = System.currentTimeMillis() - time
        }

        background(255)
        box2d.step()
        box2d.listenForCollisions()
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
        text("Klatki         : $frameRate", 10f, 20f)
        text("Moc            : $currentPower", 10f, 40f)
        text("Poziom         : ${board.level}", 10f, 60f)
        text("Punkty         : ${board.points}", 10f, 80f)
        text("Strącone bloki : ${board.downObstacles}", 10f, 100f)
        text("Strzały        : ${board.cannon.counterShot}", 10f, 120f)
    }

    override fun mousePressed() {
        startPressedTime = System.currentTimeMillis()
    }

    override fun mouseReleased() {
        board.cannon.shot(currentPower)
        currentPower = 0
        startPressedTime = null
    }


    fun beginContact(cp: Contact) {
        val f1 = cp.fixtureA
        val f2 = cp.fixtureB

        val b1 = f1.body
        val b2 = f2.body

        val o1 = b1.userData
        val o2 = b2.userData

        if (o1 != null && o2 != null && o1 is Obstacle && o2 is Bullet) {
            val p1 = o1 as Obstacle
            val p2 = o2 as Bullet
            println("kontakt")
        }

    }

    fun endContact(cp: Contact) {

    }
}