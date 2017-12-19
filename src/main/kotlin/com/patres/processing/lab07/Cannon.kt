package com.patres.processing.lab07

import com.patres.processing.fill
import org.jbox2d.common.Vec2
import processing.core.PApplet
import processing.core.PConstants.CENTER
import processing.core.PVector
import java.awt.Color

class Cannon(
        val board: Board,
        val color: Color = Color.BLACK,
        val angleSpeed: Float = 0.03f,
        val size: PVector = PVector(100f, 300f)
) {

    val pApplet = board.pApplet
    val image = board.imageKeeper.cannonImage
    val imageAngle = -Math.PI.toFloat() / 2f
    val particles = ArrayList<Bullet>()
    val position: PVector = PVector(-image.width / 2f, -image.height / 2f)
    val translatePosition: PVector = PVector(image.width / 2f, pApplet.height.toFloat() - image.height)
    var angle: Float = Math.PI.toFloat() / 4f
        set(value) {
            field = when {
                value < 0f -> 0f
                value > Math.PI.toFloat() / 2f -> Math.PI.toFloat() / 2f
                else -> value
            }
        }

    fun draw() {
        setup()
        display()
    }

    fun setup() {
        val x = pApplet.mouseX.toDouble()
        val y = (pApplet.height - pApplet.mouseY).toDouble()
        angle = Math.atan(x / y).toFloat()
    }

    fun display() {
        particles.forEach { it.display() }
        particles.removeIf { it.done() }
        pApplet.run {
            pushMatrix()
            fill(color)
            stroke(0)
            translate(translatePosition.x, translatePosition.y)
            rotate(angle + imageAngle)
            rectMode(CENTER)
            image(image, position.x, position.y)
            popMatrix()
        }
    }

    fun shot(time: Long) {
        val velocity = 10f + time / 10f
        val bulletVelocity = Vec2(velocity * PApplet.sin(angle), velocity * PApplet.cos(angle))
        val bulletAngle = angle.toDouble() +  Math.toRadians(-10.0) //+ imageAngle.toDouble()


        val xAngleTranslate = 250f * Math.sin(bulletAngle).toFloat()
        val yAngleTranslate = 250f * Math.cos(bulletAngle).toFloat()
        val bulletPosition = PVector(translatePosition.x + xAngleTranslate, translatePosition.y - yAngleTranslate)
        println("angle: ${Math.toDegrees(bulletAngle)}")
        println("xAngleTranslate: $xAngleTranslate")
        println("yAngleTranslate: $yAngleTranslate")


        particles.add(Bullet(pApplet = pApplet, cannon = this, box2d = board.box2d, velocity = bulletVelocity, position = bulletPosition))
    }

    fun changeAngle(clockwiseMovement: Boolean) {
       angle += if (clockwiseMovement) angleSpeed else -angleSpeed
    }
}