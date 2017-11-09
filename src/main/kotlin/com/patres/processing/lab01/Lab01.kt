package com.patres.processing.lab01

import com.patres.processing.lab01.Lab01.Companion.OPTIONS
import com.patres.processing.lab04.SketchLab04
import processing.core.PApplet

class Lab01 {
    companion object {
        //val OPTIONS = arrayOf("--present", SketchLab01::class.java.canonicalName)
        val OPTIONS = arrayOf(SketchLab01::class.java.canonicalName)
    }
}


fun main(args: Array<String>) {
    PApplet.main(OPTIONS)
}