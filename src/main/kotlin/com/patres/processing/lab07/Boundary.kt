package com.patres.processing.lab07

import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.BodyType

class Boundary(
        val board: Board
) {

    var body: Body
    val pApplet = board.pApplet
    val x = -pApplet.width.toFloat() / 2
    val y =  pApplet.height.toFloat() - 100f
    private val w = pApplet.width.toFloat() * 2f
    private val h = 10f
    private val box2d = board.box2d

    init {
        val sd = PolygonShape()
        val box2dW = box2d.scalarPixelsToWorld(w)
        val box2dH = box2d.scalarPixelsToWorld(h)
        sd.setAsBox(box2dW, box2dH)

        val bd = BodyDef().apply {
            type = BodyType.STATIC
            position.set(box2d.coordPixelsToWorld(x, y))
        }

        body = box2d.createBody(bd).apply {
            createFixture(sd, 1f)
        }
        body.userData = this
    }

}