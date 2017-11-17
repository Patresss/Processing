package com.patres.processing.lab05

import com.patres.processing.lab05.Lab05.Companion.OPTIONS
import processing.core.PApplet

class Lab05 {
    companion object {
        val OPTIONS = arrayOf(SketchLab05::class.java.canonicalName)
        //val OPTIONS = arrayOf("--present", SketchLab03::class.java.canonicalName)
    }
}

fun main(args: Array<String>) {
    PApplet.main(OPTIONS)
}