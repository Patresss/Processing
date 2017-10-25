package com.patres.processing.lab03

import com.patres.processing.lab03.Lab03.Companion.OPTIONS
import processing.core.PApplet

class Lab03 {
    companion object {
        val OPTIONS = arrayOf(SketchLab03::class.java.canonicalName)
        //val OPTIONS = arrayOf("--present", SketchLab03::class.java.canonicalName)
    }
}


fun main(args: Array<String>) {
    PApplet.main(OPTIONS)
}