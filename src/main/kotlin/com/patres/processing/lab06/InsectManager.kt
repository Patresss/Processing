package com.patres.processing.lab06

import com.patres.processing.lab06.model.Insect
import processing.core.PApplet
import processing.core.PImage

class InsectManager(
        val pApplet: PApplet,
        val numberOfInsects: Int = 50,
        speedY: Float = 10f
) {

    var aliveInsect: PImage = pApplet.loadImage("img/lab06/spider.png")
    var deadInsect: PImage = pApplet.loadImage("img/lab06/dead-spider.png")
    var insects = ArrayList<Insect>()
    var speedY: Float = speedY
        set(value) {
            field = when {
                value < 0f -> 0f
                else -> value
            }
        }

    init {
        addNewInsects(numberOfInsects)
    }

    fun draw() {
        insects.forEach { it.draw() }
        removeInsetsOffTheScreen()
        addNewInsects(numberOfInsects - insects.size)
    }

    private fun removeInsetsOffTheScreen() {
        insects.removeIf { it.isInScreen() }
    }

    private fun addNewInsects(number: Int) {
        for (i in 1.. number) {
            insects.add(Insect(pApplet = pApplet, manager = this))
        }
    }

}