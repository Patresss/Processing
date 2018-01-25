package com.patres.processing.lab08

import processing.core.PApplet
import processing.core.PVector

class Board(
        val pApplet: PApplet
) {

    val earth = Earth(pApplet)
    private val fractalTrees = ArrayList<FractalTree>()
    private val numberOfTrees = 5

    init {
        for (i in 1..numberOfTrees) {
            fractalTrees.add(FractalTree(this))
        }
    }

    fun draw() {
        earth.draw()
        val angleStep = 2f * PApplet.PI / fractalTrees.size
        var angle = 0f
        fractalTrees.forEach {
            pApplet.run {
                pushMatrix()
                translate(pApplet.width / 2f, pApplet.height / 2f)
                rotate(angle)
                angle += angleStep
                translate(-pApplet.width / 2f, -pApplet.height / 2f)
                it.draw()
                popMatrix()
            }
        }
    }
}