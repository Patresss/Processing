package com.patres.processing.lab08

import com.patres.processing.keepMatrix
import processing.core.PApplet
import processing.core.PVector


class FractalTree(
        val board: Board
) {

    val pApplet = board.pApplet
    val position = PVector(board.earth.position.x, board.earth.position.y)
    var startSize = 200f
    var angleBetweenBranches: Float = 0.toFloat()
    var angleOfCenterBranchFromCenter: Float = 0.toFloat()

    fun setup() {
        pApplet.run {
            size(800, 500)
            stroke(255)
            fill(255)
            noSmooth()
        }
    }

    fun draw() {
        pApplet.keepMatrix {
            translate(position.x, position.y - board.earth.radius)
            angleBetweenBranches = PApplet.map(mouseY.toFloat(), 0f, height.toFloat(), 0f, PApplet.PI)
            angleOfCenterBranchFromCenter = PApplet.map(mouseX.toFloat(), 0f, width.toFloat(), -PApplet.HALF_PI, PApplet.HALF_PI)
            drawFractal(startSize)
        }

    }

    private fun drawFractal(lineSize: Float) {
        if (lineSize > 1) {
            pApplet.run {
                if (lineSize != startSize) {
                    rotate(angleOfCenterBranchFromCenter)
                }

                translate(0f, -lineSize)
                line(0f, 0f, 0f, lineSize)

                rotate(-angleBetweenBranches)

                for (i in 0..2) {
                    pushMatrix()
                    drawFractal(lineSize / 2)
                    popMatrix()
                    rotate(angleBetweenBranches)
                }
            }

        }
    }
}