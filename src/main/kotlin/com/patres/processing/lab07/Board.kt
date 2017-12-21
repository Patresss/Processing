package com.patres.processing.lab07

import com.patres.processing.fill
import com.patres.processing.utils.RandomGenerator
import processing.core.PApplet
import shiffman.box2d.Box2DProcessing
import java.awt.Color
import java.util.*


class Board(
        val pApplet: PApplet,
        val box2d: Box2DProcessing
) {

    companion object {
        val FINAL_LEVEL = 1
    }

    val imageKeeper = ImageKeeper(pApplet)
    val cannon = Cannon(board = this)
    val image = imageKeeper.background
    val numberOfObstacle = 3
    var level = 1

    var obstacles = ArrayList<Obstacle>()
    var points = 0
    var downObstacles = 0


    val boundry = Boundary(board = this)

    init {
        newObstacle(3)
    }

    fun setup() {
        downObstacles = obstacles.filter { it.isDown() }.size
        points = (level - 1) * numberOfObstacle + downObstacles
        if(isNewLevel()) {
            obstacles.forEach { it.killBody() }
            cannon.removeBullets()
            obstacles = ArrayList()
            newObstacle(3)
            level++
        }
    }

    fun newObstacle(number: Int) {
        RandomGenerator.generateObstacleXPosition(this, number).forEach {
            obstacles.add(Obstacle(board = this, x = it))
        }
    }

    fun draw() {
        setup()
        if(level > FINAL_LEVEL) {
            val effectiveness = points * 100 / cannon.counterShot
            val wonScreen = imageKeeper.wonScreen
            pApplet.run {
                image(wonScreen, 0f, 0f)
                fill(Color.WHITE)
                textSize(50f)
                text("Punkty\t: $points", 600f, 500f)
                text("Strzały\t\t: ${cannon.counterShot}", 600f, 600f)
                text("Skuteczność\t: $effectiveness%", 600f, 700f)
            }
        } else {
            pApplet.image(image, 0f, 0f)
            cannon.draw()
            obstacles.forEach { it.display() }
        }
    }

    fun isNewLevel() = downObstacles >= numberOfObstacle


}