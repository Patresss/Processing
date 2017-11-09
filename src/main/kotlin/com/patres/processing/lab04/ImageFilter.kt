package com.patres.processing.lab04

import processing.core.PApplet
import processing.core.PImage

class ImageFilter(val original: PImage, val pApplet: PApplet) {

    fun getGrayImage(): PImage {
        val grayImage = PImage(original.width, original.height)
        for (w in 0..original.width) {
            for (h in 0..original.height) {
                val color = original.get(w, h)
                val grayColor = (getRed(color) + getGreen(color) + getBlue(color)) / 3
                grayImage.set(w, h, pApplet.color(grayColor))

            }
        }
        return grayImage
    }

    fun getNegativeImage(): PImage {
        val negativeImage = PImage(original.width, original.height)
        for (w in 0..original.width) {
            for (h in 0..original.height) {
                val orgColor = original.get(w, h)
                val newColor = pApplet.color(255 - getRed(orgColor), 255 - getGreen(orgColor), 255 - getBlue(orgColor))
                negativeImage.set(w, h, newColor)

            }
        }
        return negativeImage
    }

    fun getBinaryImage(min: Int = 0, max: Int = 255): PImage {
        val binaryImage = PImage(original.width, original.height)
        val grayImage = getGrayImage()
        for (w in 0..grayImage.width) {
            for (h in 0..grayImage.height) {
                val orgColor = grayImage.get(w, h)
                val gray = getRed(orgColor)
                val newColor = if (gray in (min)..(max)) 255 else 0
                binaryImage.set(w, h, pApplet.color(newColor))
            }
        }
        return binaryImage
    }

    fun getFlipVerticalImage(): PImage {
        val verticalImage = PImage(original.width, original.height)
        for (w in 0..original.width) {
            for (h in 0..original.height) {
                val orgColor = original.get(w, h)
                verticalImage.set(original.width - w, h, orgColor)
            }
        }
        return verticalImage
    }

    fun getFlipHorizontalImage(): PImage {
        val horizontalImage = PImage(original.width, original.height)
        for (w in 0..original.width) {
            for (h in 0..original.height) {
                val orgColor = original.get(w, h)
                horizontalImage.set(w, original.height - h, orgColor)
            }
        }
        return horizontalImage
    }

    fun getMirrorImage(): PImage {
        val verticalImage = PImage(original.width, original.height)
        for (w in 0..original.width) {
            for (h in 0..original.height) {
                val orgColor = original.get(w, h)
                if (w > original.width / 2) {
                    verticalImage.set(original.width - w + 1, h, orgColor)
                    verticalImage.set(w, h, orgColor)
                }

            }
        }
        return verticalImage
    }

    private fun getRed(color: Int) = color and 0x00FF0000 shr 16
    private fun getGreen(color: Int) = color and 0x0000FF00 shr 8
    private fun getBlue(color: Int) = color and 0x000000FF shr 16

}