package com.patres.processing.lab01

import com.patres.processing.lab01.MainApp.Companion.OPTIONS
import processing.core.PApplet

class MainApp {
    companion object {
        val OPTIONS = arrayOf("--present", SketchLab01::class.java.canonicalName)
    }
}


fun main(args: Array<String>) {
    PApplet.main(OPTIONS)
}