package com.patres.processing.lab07

import com.patres.processing.fill
import org.jbox2d.common.Vec2
import processing.core.PApplet
import processing.core.PConstants.CENTER
import processing.core.PVector
import shiffman.box2d.Box2DProcessing
import java.awt.Color

class Cannon(
        val pApplet: PApplet,
        val box2d: Box2DProcessing,
        val color: Color = Color.BLACK,
        val angleSpeed: Float = 0.03f,
        val size: PVector = PVector(100f, 300f)
) {

    val particles = ArrayList<Bullet>()
    val position: PVector = PVector(0f, 0f)
    val translatePosition: PVector = PVector(size.x / 2f, pApplet.height.toFloat() - size.x)
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
            rotate(angle)
            rectMode(CENTER)
            rect(position.x, position.y, size.x, size.y)
            popMatrix()
        }
    }

    fun shot(time: Long) {
        val velocity = time / 1f
        val bulletVelocity = Vec2(velocity * PApplet.sin(angle), velocity * PApplet.cos(angle))
        val bulletRadius = size.x / 2f * 0.85f
        particles.add(Bullet(pApplet = pApplet, box2d = box2d, radius = bulletRadius, velocity = bulletVelocity, position = translatePosition))
    }

    fun changeAngle(clockwiseMovement: Boolean) {
        angle += if (clockwiseMovement) angleSpeed else -angleSpeed
    }
}