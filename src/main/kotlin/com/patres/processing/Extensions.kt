package com.patres.processing

import processing.core.PApplet
import processing.core.PImage
import java.awt.Color

fun PApplet.fill(color: Color) {
    this.fill(color.rgb)
}

fun Color.getContrastColor(): Color {
    val y = ((299 * this.red + 587 * this.green + 114 * this.blue) / 1000).toDouble()
    return if (y >= 128) Color.black else Color.white
}

fun Float.getPositiveOrNegativeValue(): Float = if (this >= 0f) 1f else -1f

fun PImage.flipVerticalImage(): PImage {
    val verticalImage = PImage(this.width, this.height)
    for (w in 0..this.width) {
        for (h in 0..this.height) {
            val orgColor = this.get(w, h)
            verticalImage.set(this.width - w, h, orgColor)
        }
    }
    return verticalImage
}
