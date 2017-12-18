package com.patres.processing.lab07

import org.jbox2d.collision.shapes.CircleShape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.BodyType
import org.jbox2d.dynamics.FixtureDef
import processing.core.PApplet
import processing.core.PVector
import shiffman.box2d.Box2DProcessing

class Bullet(
        val pApplet: PApplet,
        val box2d: Box2DProcessing,
        var radius: Float = 100f,
        var position: PVector = PVector(100f, pApplet.height.toFloat() - 100f),
        var velocity: Vec2 = Vec2(30f, 15f)
) {

    lateinit var body: Body

    init {
        makeBody(position.x, position.y, radius)
    }

    fun killBody() {
        box2d.destroyBody(body)
    }

    fun done(): Boolean {
        val pos = box2d.getBodyPixelCoord(body)
        if (pos.y > pApplet.height + radius * 2) {
            killBody()
            return true
        }
        return false
    }

    fun display() {
        val pos = box2d.getBodyPixelCoord(body)
        pApplet.run {
            pushMatrix()
            translate(pos.x, pos.y)
            fill(175)
            stroke(0)
            strokeWeight(1f)
            ellipse(0f, 0f, radius * 2, radius * 2)
            popMatrix()
        }
    }

    fun makeBody(x: Float, y: Float, r: Float) {
        val bd = BodyDef().apply {
            position = box2d.coordPixelsToWorld(x, y)
            type = BodyType.DYNAMIC


        }

        val cs = CircleShape().apply {
            m_radius = box2d.scalarPixelsToWorld(r)
        }
        val fd = FixtureDef().apply {
            shape = cs
            density = 1f
            friction = 0.01f
            restitution = 0.3f
        }
        body = box2d.world.createBody(bd).apply {
            createFixture(fd)
            linearVelocity = velocity
            angularVelocity = 10f
        }
    }

}