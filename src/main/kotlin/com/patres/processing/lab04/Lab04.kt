package com.patres.processing.lab04

import com.patres.processing.lab04.Lab04.Companion.OPTIONS
import processing.core.PApplet

class Lab04 {
    companion object {
        val OPTIONS = arrayOf(SketchLab04::class.java.canonicalName)
        //val OPTIONS = arrayOf("--present", SketchLab03::class.java.canonicalName)
    }
}


fun main(args: Array<String>) {
    PApplet.main(OPTIONS)
}