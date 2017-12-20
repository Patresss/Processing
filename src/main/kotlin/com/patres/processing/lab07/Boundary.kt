package com.patres.processing.lab07

import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.BodyType

class Boundary(
        val board: Board
) {

    var b: Body
    val box2d = board.box2d
    val pApplet = board.pApplet
    val x = -pApplet.width.toFloat() / 2
    val y =  pApplet.height.toFloat() - 100f
    val w = pApplet.width.toFloat() * 2f
    val h = 10f

    init {
        val sd = PolygonShape()
        val box2dW = box2d.scalarPixelsToWorld(w)
        val box2dH = box2d.scalarPixelsToWorld(h)
        sd.setAsBox(box2dW, box2dH)

        val bd = BodyDef().apply {
            type = BodyType.STATIC
            position.set(box2d.coordPixelsToWorld(x, y))
        }

        b = box2d.createBody(bd).apply {
            createFixture(sd, 1f)
        }
    }

    fun display() {
        pApplet.run {
            fill(0)
            stroke(0)
            strokeWeight(1f)
            pushMatrix()
            translate(x, y)
            rect(0f, 0f, w, h)
            popMatrix()
        }
    }

}