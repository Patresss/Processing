package com.patres.processing.lab07

import org.jbox2d.collision.shapes.CircleShape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.BodyType
import org.jbox2d.dynamics.FixtureDef
import processing.core.PApplet
import processing.core.PConstants
import processing.core.PVector
import shiffman.box2d.Box2DProcessing

class Bullet(
        val cannon: Cannon,
        val pApplet:PApplet = cannon.pApplet,
        val box2d: Box2DProcessing,
        var radius: Float = 20f,
        var position: PVector,
        var velocity: Vec2 = Vec2(30f, 15f)
) {

    lateinit var body: Body
    val image = cannon.board.imageKeeper.snowBall

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
        val a = body.getAngle()
        pApplet.run {
            pushMatrix()
            translate(pos.x , pos.y)
            rotate(-a)
            //rectMode(PConstants.CENTER)
            image(image, -image.width / 2f, -image.height / 2f)
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

isBullet = true
            createFixture(fd)
            linearVelocity = velocity
            angularVelocity = 10f
        }
    }

}