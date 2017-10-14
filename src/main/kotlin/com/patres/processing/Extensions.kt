package com.patres.processing

import processing.core.PApplet
import java.awt.Color

fun PApplet.fill(color: Color) {
    this.fill(color.rgb)
}