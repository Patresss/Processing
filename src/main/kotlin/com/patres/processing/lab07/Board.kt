package com.patres.processing.lab07

import com.patres.processing.utils.RandomGenerator
import processing.core.PApplet
import processing.core.PVector
import shiffman.box2d.Box2DProcessing
import java.util.*

class Board(
        val pApplet: PApplet,
        val box2d: Box2DProcessing
) {


    val imageKeeper = ImageKeeper(pApplet)
    val cannon = Cannon(board = this)
    val image = imageKeeper.background
    val numberOfObstacle = 3
    val obstacles = ArrayList<Obstacle>()

    val boundry = Boundary(board = this)

    init {
        newObstacle(3)
    }

    fun newObstacle(number: Int) {
        RandomGenerator.generateObstacleXPosition(this, number).forEach {
            obstacles.add(Obstacle(board = this, x = it))
        }

    }

    fun draw() {
        pApplet.image(image, 0f, 0f)
        cannon.draw()
        obstacles.forEach { it.display() }
    }

}