package com.patres.processing.utils

import com.patres.processing.lab07.Board
import com.patres.processing.lab07.Obstacle
import processing.core.PVector
import java.awt.Color
import java.util.*


class RandomGenerator {
    companion object {
        var random = Random()
        fun generateFloat(min: Float = 0f, max: Float): Float = random.nextFloat() * (max - min) + min
        fun generateColor(): Color = Color(random.nextInt(256*256*256))
        fun generateVector(min: Float = 0f, max: Float): PVector = PVector(generateFloat(min, max), generateFloat(min, max))
        fun generateObstacleXPosition(board: Board, numberOfObstacle: Int): List<Float> {
            val min = board.cannon.image.width + 100f
            val max = board.pApplet.width.toFloat() - board.cannon.image.width
            val obstacleX = arrayListOf(generateFloat(min = min, max = max))
            while(obstacleX.size < numberOfObstacle) {
                val generateNumber = generateFloat(min = min, max = max)
                if(obstacleX.all{ Math.abs(it - generateNumber) >  board.imageKeeper.ice.width}) {
                    obstacleX.add(generateNumber)
                }
            }
            return obstacleX
        }
    }
}

