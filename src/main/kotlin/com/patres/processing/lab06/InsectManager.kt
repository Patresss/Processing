package com.patres.processing.lab06

import com.patres.processing.lab06.model.Insect
import processing.core.PApplet
import processing.core.PImage

class InsectManager(
        val pApplet: PApplet,
        numberOfInsects: Int = 50
) {

    var aliveInsect: PImage = pApplet.loadImage("img/lab06/spider.png")
    var deadInsect: PImage = pApplet.loadImage("img/lab06/dead-spider.png")
    var insects = ArrayList<Insect>()
    var frameSpeedBooster = 60f

    var numberOfInsects: Int = numberOfInsects
        set(value) {
            field = when {
                value < 0 -> 0
                else -> value
            }
        }

    init {
        addNewInsects(numberOfInsects)
    }

    fun draw() {
        frameSpeedBooster = pApplet.frameRate / 60f
        insects.forEach { it.draw() }
        removeInsectOffTheScreen()
        addNewInsects(numberOfInsects - insects.size)
        insects.removeIf { insects.indexOf(it) + 1 >= numberOfInsects }

    }

    private fun removeInsectOffTheScreen() {
        insects.removeIf { it.isInScreen() }
    }

    private fun addNewInsects(number: Int) {
        for (i in 1..number) {
            insects.add(Insect(pApplet = pApplet, manager = this))
        }
    }

}