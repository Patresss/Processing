package com.patres.processing.lab05

import com.patres.processing.lab05.model.SoapBubble
import processing.core.PApplet
import processing.core.PImage

class SoapBubbleManager(
        val pApplet: PApplet,
        val speedX: Float = 3f,
        speedY: Float = 10f,
        frequencyOfNewBubbles: Int = 1000
) {
    var bubbles = ArrayList<SoapBubble>()
    var img = pApplet.loadImage("img/lab05/bubble.png")?: PImage()
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
    private var lastCreationTime = System.currentTimeMillis()

    fun draw() {
        if (shouldDrawNewBubble()) {
            bubbles.add(SoapBubble(pApplet = pApplet, manager = this))
            lastCreationTime = System.currentTimeMillis()
        }
        removeBubbleOffTheScreen()

        bubbles.forEach { it.draw() }
    }

    fun removeLife(touchedBubbles: List<SoapBubble>) {
        touchedBubbles.forEach { it.numberOfLife-- }
        bubbles.removeAll(touchedBubbles.filter { it.numberOfLife <= 0 })
    }

    private fun removeBubbleOffTheScreen() {
        bubbles.removeIf { it.isInScreen() }
    }

    private fun shouldDrawNewBubble(): Boolean = (System.currentTimeMillis() - lastCreationTime) > frequencyOfNewBubbles

}