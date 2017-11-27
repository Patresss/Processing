package com.patres.processing.lab06

import com.patres.processing.lab06.Lab06.Companion.OPTIONS
import processing.core.PApplet

class Lab06 {
    companion object {
        val OPTIONS = arrayOf(SketchLab06::class.java.canonicalName)
        //val OPTIONS = arrayOf("--present", SketchLab03::class.java.canonicalName)
    }
}

fun main(args: Array<String>) {
    PApplet.main(OPTIONS)
}