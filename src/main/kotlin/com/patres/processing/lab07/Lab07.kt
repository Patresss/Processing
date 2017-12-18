package com.patres.processing.lab07

import com.patres.processing.lab07.Lab07.Companion.OPTIONS
import processing.core.PApplet

class Lab07 {
    companion object {
        val OPTIONS = arrayOf(SketchLab07::class.java.canonicalName)
        //val OPTIONS = arrayOf("--present", SketchLab03::class.java.canonicalName)
    }
}

fun main(args: Array<String>) {
     PApplet.main(OPTIONS)
}