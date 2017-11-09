package com.patres.processing.lab02.model

import com.patres.processing.fill
import com.patres.processing.getContrastColor
import com.patres.processing.getPositiveOrNegativeValue
import com.patres.processing.lab02.SketchLab02
import com.patres.processing.utils.RandomGenerator
import processing.core.PApplet
import processing.core.PConstants.CENTER
import java.awt.Color

class BallModel(
        val pApplet: PApplet,
        var positionX: Float = 0.0f,
        var positionY: Float = 0.0f,
        radius: Float = 100.0f,
        var color: Color = Color.BLACK,
        var numberOfLife: Int = 3,
        var speedX: Float = 10f,
        var speedY: Float = 10f
) {

    companion object {
        val MIN_RADIUS = 5f
        val MAX_RADIUS = 100f
        val MIN_RADIUS_VIBRATE = 0.95f
        val MAX_RADIUS_VIBRATE = 1.05f
        val HALO_SIZE = 10f
    }

    var currentRadiusStep: Float = radius * 0.01F
    var shouldVibrate: Boolean = false
    var neighbour: BallModel? = null
    var currentRadius: Float = radius
    var radius: Float = radius
        set(value) {
            field = when {
                value > MAX_RADIUS -> MAX_RADIUS
                value < MIN_RADIUS -> MIN_RADIUS
                else -> value
            }
            currentRadius = field
        }

    var mass: Float = 0.0f
        get() = (Math.PI * radius * radius).toFloat()

    fun draw() {
        pApplet.fill(color)
        pApplet.noStroke()
        if (shouldVibrate) vibrate()
        move()
        pApplet.ellipse(positionX, positionY, currentRadius, currentRadius)
        drawNumberOfLife()
    }

    fun drawNeighbourEffect() {
        neighbour?.let { ball ->
            drawHalo()
            pApplet.line(positionX, positionY, ball.positionX, ball.positionY)
            pApplet.fill(color)
        }
    }

    private fun drawHalo() {
        pApplet.noFill()
        pApplet.stroke(255)
        pApplet.ellipse(positionX, positionY, currentRadius + HALO_SIZE, currentRadius + HALO_SIZE)
    }


    private fun drawNumberOfLife() {
        pApplet.fill(color.getContrastColor())
        pApplet.textAlign(CENTER)
        pApplet.text(numberOfLife, positionX, positionY)
    }

    fun move() {
        positionX += speedX
        positionY += speedY

        if (positionX - currentRadius / 2.0 < 0) {
            speedX = Math.abs(speedX)
        } else if (positionX + currentRadius / 2.0 > SketchLab02.SIZE_X) {
            speedX = -Math.abs(speedX)
        }

        if (positionY - currentRadius / 2.0 < 0) {
            speedY = Math.abs(speedY)
        } else if (positionY + currentRadius / 2.0 > SketchLab02.SIZE_Y) {
            speedY = -Math.abs(speedY)
        }
    }

    private fun vibrate() {
        if (currentRadius > radius * MAX_RADIUS_VIBRATE || currentRadius < radius * MIN_RADIUS_VIBRATE) {
            currentRadiusStep = - currentRadiusStep
        }
        currentRadius += currentRadiusStep
    }

    fun randomPosition() {
        positionX = RandomGenerator.generateFloat(SketchLab02.BALL_POSITION_X_MIN + currentRadius, SketchLab02.BALL_POSITION_X_MAX - currentRadius)
        positionY = RandomGenerator.generateFloat(SketchLab02.BALL_POSITION_Y_MIN + currentRadius, SketchLab02.BALL_POSITION_Y_MAX - currentRadius)
    }

    fun increaseSpeed(value: Float) {
        val newValueX = speedX.getPositiveOrNegativeValue() * value
        speedX += newValueX
        val newValueY = speedY.getPositiveOrNegativeValue() * value
        speedY += newValueY
    }

    fun reduceSpeed(value: Float) {
        val newValueX = speedX.getPositiveOrNegativeValue() * value
        if (newValueX >= Math.abs(speedX)) {
            speedX = 0f
        } else {
            speedX -= newValueX
        }

        val newValueY = speedY.getPositiveOrNegativeValue() * value
        if (newValueY >= Math.abs(speedY)) {
            speedY = 0f
        } else {
            speedY -= newValueY
        }

    }

    fun collide(balls: List<BallModel>) {
        balls.forEach({ ball ->
            val distance = getDistance(ball)
            val minDist = ball.currentRadius / 2f + currentRadius / 2f
            if (distance < minDist) {
                val nx = (ball.positionX - positionX) / distance
                val ny = (ball.positionY - positionY) / distance
                val p = 2f * (this.speedX * nx + this.speedY * ny - ball.speedX * nx - ball.speedY * ny) / (this.mass + ball.mass)
                this.speedX -= p * ball.mass * nx
                this.speedY -= p * ball.mass * ny
                ball.speedX += p * this.mass * nx
                ball.speedY += p * this.mass * ny
            }
        })
    }

    fun getDistance(ball: BallModel): Float {
        val dx = ball.positionX - positionX
        val dy = ball.positionY - positionY
        return PApplet.sqrt(dx * dx + dy * dy)
    }

}