package com.patres.processing.lab06.model

import com.patres.processing.lab06.InsectManager
import com.patres.processing.lab06.SketchLab06
import com.patres.processing.utils.RandomGenerator
import processing.core.PApplet
import processing.core.PVector


class Insect(
        val pApplet: PApplet,
        val manager: InsectManager,
        private val radius: Float = RandomGenerator.generateFloat(min = 10f, max = 30f),
        private var position: PVector = PVector(RandomGenerator.generateFloat(max = SketchLab06.CAMERA_RESOLUTION_WIDTH - radius), RandomGenerator.generateFloat(max = SketchLab06.CAMERA_RESOLUTION_HEIGHT - radius)),
        private var moveSpeed: PVector = RandomGenerator.generateVector(-10f, 10f)
) {

    companion object {
        val NOISE_SCALE = 0.03f
    }

    var alive = true

    fun draw() {
        move()
        val scaledX = (position.x - radius) * SketchLab06.SCALE
        val scaledY = (position.y - radius) * SketchLab06.SCALE
        val scaledRadius = radius * SketchLab06.SCALE
        val image = if (alive) manager.aliveInsect else manager.deadInsect
        pApplet.image(image, scaledX, scaledY, scaledRadius * 2, scaledRadius * 2)
    }

    fun isInScreen(): Boolean = position.y - radius > pApplet.height

    fun containsPixel(x: Int, y: Int): Boolean = distanceFromCenter(x, y) < radius

    fun getNumberOfPixels(): Int = (Math.pow(radius * 2.0, 2.0).toInt() * Math.PI / 4.0).toInt()

    private fun move() {
        if (alive) {
            aliveMove()
        } else {
            position.y += Math.abs(moveSpeed.y) * 3
        }

    }

    private fun aliveMove() {
        position.x += moveSpeed.x * pApplet.noise(NOISE_SCALE * pApplet.frameCount.toFloat() * manager.insects.indexOf(this))
        position.y += moveSpeed.y * pApplet.noise(NOISE_SCALE * pApplet.frameCount.toFloat() / 3 * manager.insects.indexOf(this))

        if (position.x - radius < 0) {
            moveSpeed.x = Math.abs(moveSpeed.x)
        } else if (position.x + radius > SketchLab06.CAMERA_RESOLUTION_WIDTH) {
            moveSpeed.x = -Math.abs(moveSpeed.x)
        }

        if (position.y - radius < 0) {
            moveSpeed.y = Math.abs(moveSpeed.y)
        } else if (position.y + radius > SketchLab06.CAMERA_RESOLUTION_HEIGHT) {
            moveSpeed.y = -Math.abs(moveSpeed.y)
        }
    }

    private fun distanceFromCenter(x: Int, y: Int): Double = Math.sqrt(Math.pow(position.x.toDouble() - x, 2.0) + Math.pow(position.y.toDouble() - y, 2.0))

}