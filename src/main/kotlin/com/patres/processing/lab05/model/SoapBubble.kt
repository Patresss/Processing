package com.patres.processing.lab05.model

import com.patres.processing.fill
import com.patres.processing.lab05.SketchLab05
import com.patres.processing.lab05.SoapBubbleFactory
import com.patres.processing.utils.RandomGenerator
import processing.core.PApplet
import processing.core.PVector
import java.awt.Color

class SoapBubble(
        val pApplet: PApplet,
        val factory: SoapBubbleFactory,
        val radius: Float = RandomGenerator.generateFloat(min = 10f * SketchLab05.SCALE, max = 30f * SketchLab05.SCALE),
        var position: PVector = PVector(RandomGenerator.generateFloat(max = SketchLab05.CAMERA_RESOLUTION_WIDTH * SketchLab05.SCALE), -radius),
        var color: Color = Color.RED,
        var numberOfLife: Int = 3,
        var speedVectorAngle: PVector = RandomGenerator.generateVector(-0.05f, 0.05f)
) {

    var positionXAngle = 0.0
    var positionYAngle = 0.0


    fun draw() {
        pApplet.fill(color)
        pApplet.noStroke()
        move()
        pApplet.ellipse(position.x, position.y, radius, radius)
    }

    fun move() {
        positionXAngle += speedVectorAngle.x
        positionYAngle += speedVectorAngle.y
        position.x += Math.sin(positionXAngle).toFloat() * factory.speedX
        position.y += Math.abs(Math.cos(positionYAngle).toFloat()) * factory.speedY
    }

    fun isInScreen(): Boolean = position.y  - radius> SketchLab05.CAMERA_RESOLUTION_HEIGHT * SketchLab05.SCALE

    fun containsPixel(x: Int, y: Int): Boolean = distanceFromCenter(x, y) < radius

    fun distanceFromCenter(x: Int, y: Int): Double = Math.sqrt(Math.pow(position.x.toDouble() - x, 2.0) + Math.pow(position.y.toDouble() - y, 2.0))

    fun getNumberOfPixels(): Int = (Math.pow(radius * 2.0, 2.0).toInt() * Math.PI / 4.0).toInt()

}