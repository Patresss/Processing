package com.patres.processing.lab02

import com.patres.processing.lab02.Lab02.Companion.OPTIONS
import processing.core.PApplet

class Lab02 {
    companion object {
        val OPTIONS = arrayOf("--present", SketchLab02::class.java.canonicalName)
    }
}


fun main(args: Array<String>) {
    PApplet.main(OPTIONS)
}