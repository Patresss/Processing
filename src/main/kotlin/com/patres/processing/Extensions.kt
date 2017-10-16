package com.patres.processing

import processing.core.PApplet
import java.awt.Color

fun PApplet.fill(color: Color) {
    this.fill(color.rgb)
}

fun Color.getContrastColor(): Color {
    val y = ((299 * this.red + 587 * this.green + 114 * this.blue) / 1000).toDouble()
    return if (y >= 128) Color.black else Color.white
}

fun Float.getPositiveOrNegativeValue(): Float {
    return if (this >= 0f) 1f else -1f
}