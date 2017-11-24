package com.patres.processing.lab05.model

import com.patres.processing.lab05.SketchLab05
import com.patres.processing.lab05.SoapBubbleManager
import com.patres.processing.utils.RandomGenerator
import processing.core.PApplet
import processing.core.PVector

class SoapBubble(
        val pApplet: PApplet,
        val manager: SoapBubbleManager,
        var numberOfLife: Int = 1,
        private val radius: Float = RandomGenerator.generateFloat(min = 10f, max = 30f),
        private var position: PVector = PVector(RandomGenerator.generateFloat(min = radius, max = SketchLab05.CAMERA_RESOLUTION_WIDTH - radius), -radius),
        private var speedVectorAngle: PVector = RandomGenerator.generateVector(-0.05f, 0.05f)
) {

    var positionXAngle = 0.0
    var positionYAngle = 0.0
    var touched = false


    fun draw() {
        move()
        val scaledX = (position.x - radius) * SketchLab05.SCALE
        val scaledY = (position.y - radius) * SketchLab05.SCALE
        val scaledRadius = radius * SketchLab05.SCALE
        pApplet.image(manager.img, scaledX, scaledY , scaledRadius * 2, scaledRadius * 2)
    }

    fun isInScreen(): Boolean = position.y - radius > pApplet.height

    fun containsPixel(x: Int, y: Int): Boolean = distanceFromCenter(x, y) < radius

    fun getNumberOfPixels(): Int = (Math.pow(radius * 2.0, 2.0).toInt() * Math.PI / 4.0).toInt()

    private fun move() {
        positionXAngle += speedVectorAngle.x
        positionYAngle += speedVectorAngle.y
        position.x += Math.sin(positionXAngle).toFloat() * manager.speedX
        position.y += Math.abs(Math.cos(positionYAngle).toFloat()) * manager.speedY
    }

    private fun distanceFromCenter(x: Int, y: Int): Double = Math.sqrt(Math.pow(position.x.toDouble() - x, 2.0) + Math.pow(position.y.toDouble() - y, 2.0))

}