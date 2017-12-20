package com.patres.processing.lab07

import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.BodyType
import org.jbox2d.dynamics.FixtureDef


class Obstacle(
        val board: Board,
        val x: Float,
        val y: Float = 680f
) {
    val pApplet = board.pApplet
    val box2d = board.box2d
    val image = board.imageKeeper.ice
    lateinit var body: Body

    init {
        makeBody(x, y)
    }

    fun display() {
        val pos = box2d.getBodyPixelCoord(body)
        val a = body.angle
        pApplet.run {
            pushMatrix()
            translate(pos.x, pos.y)
            rotate(-a)
            image(image, -image.width / 2f, -image.height / 2f)
            popMatrix()
        }
    }

    fun makeBody(x: Float, y: Float) {
        val w = image.width.toFloat()
        val h = image.height.toFloat()
        val bd = BodyDef().apply {
            position = box2d.coordPixelsToWorld(x, y)
            type = BodyType.DYNAMIC
        }

        val polygonShape = PolygonShape()
        val box2dW = box2d.scalarPixelsToWorld(w / 2)
        val box2dH = box2d.scalarPixelsToWorld(h / 2)
        polygonShape.setAsBox(box2dW, box2dH)

        val fd = FixtureDef().apply {
            shape = polygonShape
            density = 0.1f
            friction = 0.3f
            restitution = 0.5f
        }

        body = box2d.world.createBody(bd).apply {
            createFixture(fd)
        }
    }
}