package com.patres.processing.lab01

import com.patres.processing.lab02.model.BallModel
import com.patres.processing.utils.RandomGenerator
import processing.core.PApplet
import processing.core.PConstants

class SketchLab01 : PApplet() {

    companion object {
        val SIZE_X = 1000
        val SIZE_Y = 1000

        val INIT_NUMBER_OF_BALLS = 3

        val BALL_POSITION_X_MIN = 0f
        val BALL_POSITION_X_MAX = SIZE_X
        val BALL_POSITION_Y_MIN = 0f
        val BALL_POSITION_Y_MAX = SIZE_Y

        val BALL_RADIUS_MIN = 20f
        val BALL_RADIUS_MAX = 100f

        val BALL_SPEED_X_MIN = 1f
        val BALL_SPEED_X_MAX = 30f
        val BALL_SPEED_Y_MIN = 1f
        val BALL_SPEED_Y_MAX = 30f
    }

    private val balls: ArrayList<BallModel> = ArrayList()

    override fun settings() {
        size(SIZE_X, SIZE_Y)
    }

    override fun setup() {
        for (i in 1..INIT_NUMBER_OF_BALLS) balls.add(generateBallModel())
    }

    override fun draw() {
        background(255)

        balls.forEach { it.draw() }
    }

    override fun keyPressed() {
        if (key.toInt() == PConstants.CODED) {
            if (keyCode == PConstants.UP) {
                balls.add(generateBallModel())
            } else if (keyCode == PConstants.DOWN && balls.size > 0) {
                balls.removeAt(balls.size - 1)
            }
        }
    }

    private fun generateBallModel(): BallModel {
        val generatedRadius = RandomGenerator.generateFloat(BALL_RADIUS_MIN, BALL_RADIUS_MAX)
        return BallModel(
                pApplet = this,
                positionX = RandomGenerator.generateFloat(BALL_POSITION_X_MIN + generatedRadius, BALL_POSITION_X_MAX - generatedRadius),
                positionY = RandomGenerator.generateFloat(BALL_POSITION_Y_MIN + generatedRadius, BALL_POSITION_Y_MAX - generatedRadius),
                radius = generatedRadius,
                color = RandomGenerator.generateColor(),
                speedX = RandomGenerator.generateFloat(BALL_SPEED_X_MIN, BALL_SPEED_X_MAX),
                speedY = RandomGenerator.generateFloat(BALL_SPEED_Y_MIN, BALL_SPEED_Y_MAX))
    }

}