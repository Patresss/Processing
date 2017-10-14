package com.patres.processing.model

import com.patres.processing.fill
import processing.core.PApplet
import java.awt.Color

class BallModel(
        val pApplet: PApplet,
        var positionX: Float = 0.0f,
        var positionY: Float = 0.0f,
        var radius: Float = 100.0f,
        var currentRadius: Float = radius,
        var color: Color = Color.BLACK,
        var speedX: Float = 10f,
        var speedY: Float = 10f

) {

    var currentSpeedX: Float = 10f
    var currentSpeedY: Float = 10f
    var currentRadiousStep: Float = radius * 0.1F

    fun draw() {
        pApplet.fill(color)
        pApplet.noStroke()
        vibrate()
        move()
        pApplet.ellipse(positionX, positionY, currentRadius, currentRadius)
    }

    fun move() {
        if (positionX - radius / 2.0 < 0) {
            currentSpeedX = speedX
        } else if (positionX + radius / 2.0 > 1000) {
            currentSpeedX = -speedX
        }

        if (positionY - radius / 2.0 < 0) {
            currentSpeedY = speedY
        } else if (positionY + radius / 2.0 > 1000) {
            currentSpeedY = -speedY
        }

        positionX += currentSpeedX
        positionY += currentSpeedY

    }

    fun vibrate() {
        if (currentRadius > radius * 1.1 || currentRadius < radius * 0.9) {
            currentRadiousStep = -currentRadiousStep
        }
        currentRadius += currentRadiousStep
    }

}