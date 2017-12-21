package com.patres.processing.lab07

import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.BodyType
import org.jbox2d.dynamics.FixtureDef


class Obstacle(
        val board: Board,
        val x: Float,
        val y: Float = 680f
) {

    companion object {
        val ANGLE_DOWN = Math.toRadians(60.0).toFloat()
        val DOWN_MOVE_EPSILON = 0.1f
    }

    val pApplet = board.pApplet
    var position = Vec2()
    var previousPosition: Vec2? = null
    val box2d = board.box2d
    val image = board.imageKeeper.ice
    lateinit var body: Body

    init {
        makeBody(x, y)
    }

    fun display() {
        previousPosition = Vec2(position)
        position = box2d.getBodyPixelCoord(body)

        val a = body.angle
        pApplet.run {
            pushMatrix()
            translate(position.x, position.y)
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
            density = 1f
            friction = 0.3f
            restitution = 0.5f
        }

        body = box2d.world.createBody(bd).apply {
            createFixture(fd)
        }
        body.userData = this
    }

    fun isDown() : Boolean {
        previousPosition?.let { previousPosition ->
            return Math.abs(body.angle) > ANGLE_DOWN &&
                    Math.abs(previousPosition.x - position.x) <= DOWN_MOVE_EPSILON &&
                    Math.abs(previousPosition.y - position.y) <= DOWN_MOVE_EPSILON
        }
        return false
    }

    fun killBody() {
        box2d.destroyBody(body)
    }
}