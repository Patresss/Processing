package com.patres.processing.lab05

import com.patres.processing.lab05.model.SoapBubble
import processing.core.PApplet

class SoapBubbleManager(
        val pApplet: PApplet,
        val speedX: Float = 3f,
        speedY: Float = 10f,
        frequencyOfNewBubbles: Int = 1000
) {

    var bubbles = ArrayList<SoapBubble>()
    var lastCreationTime = System.currentTimeMillis()
    var img = pApplet.loadImage("img/lab05/bubble.png")
    var frequencyOfNewBubbles: Int = frequencyOfNewBubbles
        set(value) {
            field = when {
                value < 0 -> 0
                else -> value
            }
        }

    var speedY: Float = speedY
        set(value) {
            field = when {
                value < 0f -> 0f
                else -> value
            }
        }

    fun draw() {
        if (shouldDrawNewBubble()) {
            bubbles.add(SoapBubble(pApplet = pApplet, manager = this))
            lastCreationTime = System.currentTimeMillis()
        }
        removeBubbleOffTheScreen()

        bubbles.forEach { it.draw() }
    }

    fun removeBubbleOffTheScreen() {
        bubbles.removeIf { it.isInScreen() }
    }

    fun shouldDrawNewBubble(): Boolean = (System.currentTimeMillis() - lastCreationTime) > frequencyOfNewBubbles

    fun removeLife(touchedBubbles: List<SoapBubble>) {
        touchedBubbles.forEach { it.numberOfLife-- }
        bubbles.removeAll(touchedBubbles.filter { it.numberOfLife <= 0 })
    }

}