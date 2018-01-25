package com.patres.processing.lab08

import com.patres.processing.lab08.Lab08.Companion.OPTIONS
import processing.core.PApplet

class Lab08 {
    companion object {
        val OPTIONS = arrayOf(SketchLab08::class.java.canonicalName)
        //val OPTIONS = arrayOf("--present", SketchLab03::class.java.canonicalName)
    }
}

fun main(args: Array<String>) {
     PApplet.main(OPTIONS)
}