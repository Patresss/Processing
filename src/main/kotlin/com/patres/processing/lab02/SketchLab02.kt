package com.patres.processing.lab02

import com.patres.processing.fill
import com.patres.processing.lab02.model.BallModel
import com.patres.processing.utils.RandomGenerator
import processing.core.PApplet
import processing.core.PConstants
import java.awt.Color

class SketchLab02 : PApplet() {

    companion object {
        val SIZE_X = 500
        val SIZE_Y = 500

        val INIT_NUMBER_OF_BALLS = 3

        val BALL_POSITION_X_MIN = 0f
        val BALL_POSITION_X_MAX = SIZE_X
        val BALL_POSITION_Y_MIN = 0f
        val BALL_POSITION_Y_MAX = SIZE_Y

        val BALL_RADIUS_MIN = 10f
        val BALL_RADIUS_MAX = 100f
        val BALL_RADIUS_CHANGE_STEP = 10f

        val BALL_SPEED_X_MIN = 1f
        val BALL_SPEED_X_MAX = 3f
        val BALL_SPEED_Y_MIN = 1f
        val BALL_SPEED_Y_MAX = 3f

        val INCREASE_SPEED_VALUE = 1f
        val NUMBERS_OF_NEAREST_NEIGHBORS = 3
    }

    private val balls: ArrayList<BallModel> = ArrayList()
    private var clickedBalls: List<BallModel> = ArrayList()

    override fun settings() {
        size(SIZE_X, SIZE_Y)
    }

    override fun setup() {
        for (i in 1..INIT_NUMBER_OF_BALLS) balls.add(generateBallModel())
    }

    override fun draw() {
        background(180f, 180f, 180f)
        val ballsToCollide = ArrayList<BallModel>(balls)
        balls.forEach {
            ballsToCollide.remove(it)
            it.collide(ballsToCollide)
        }
        balls.forEach { it.draw() }
        clickedBalls.forEach { getNearestNeighbors(it).forEach { ball -> ball.neighbour = it } }
        balls.forEach {
            it.drawNeighbourEffect()
            it.neighbour = null
        }
    }


    override fun keyPressed() {
        when (keyCode) {
            PConstants.UP -> balls.add(generateBallModel())
            PConstants.DOWN -> if (balls.size > 0) balls.removeAt(balls.size - 1)
            PConstants.LEFT -> balls.forEach { it.reduceSpeed(INCREASE_SPEED_VALUE) }
            PConstants.RIGHT -> balls.forEach { it.increaseSpeed(INCREASE_SPEED_VALUE) }
        }

        if (key == ' ') {
            balls.forEach { it.randomPosition() }
        }
    }

    override fun mousePressed() {
        when (mouseButton) {
            CENTER -> reduceNumberOfLife()
            RIGHT -> balls.filter { overBall(it) }.forEach { it.radius += BALL_RADIUS_CHANGE_STEP }
            LEFT -> leftMousePressed()
        }
    }

    private fun leftMousePressed() {
        clickedBalls = balls.filter { overBall(it) }.toList()
        balls.forEach {
            it.shouldVibrate = false
            it.neighbour = null
        }

        clickedBalls.forEach {
            it.radius -= BALL_RADIUS_CHANGE_STEP
            it.shouldVibrate = true
            getNearestNeighbors(it).forEach { ball -> ball.neighbour = it }
        }
    }

    private fun getNearestNeighbors(ball: BallModel): List<BallModel> {
        val listOfBalls = balls.filter { it != ball }
        return if (listOfBalls.size < NUMBERS_OF_NEAREST_NEIGHBORS) {
            listOfBalls
        } else {
            listOfBalls.sortedBy { it.getDistance(ball) }.take(NUMBERS_OF_NEAREST_NEIGHBORS)
        }
    }

    fun reduceNumberOfLife() {
        balls.filter { overBall(it) }.forEach { it.numberOfLife-- }
        balls.filter { it.numberOfLife <= 0 }.forEach { balls.remove(it) }
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

    private fun overBall(ball: BallModel): Boolean {
        val disX = ball.positionX - mouseX
        val disY = ball.positionY - mouseY
        return sqrt(sq(disX) + sq(disY)) < ball.currentRadius / 2
    }


}