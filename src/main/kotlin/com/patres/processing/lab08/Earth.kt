package com.patres.processing.lab08

import processing.core.PApplet
import processing.core.PVector

class Earth(
        val pApplet: PApplet
) {

    val position = PVector(pApplet.width / 2f, pApplet.height / 2f)
    val radius = 100f

    fun draw() {
        pApplet.run {
            pushMatrix()
            stroke(0)
            ellipse(position.x, position.y, 2 * radius, 2 * radius)
            popMatrix()
        }
    }
}