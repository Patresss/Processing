package com.patres.processing.lab05

import com.patres.processing.lab05.model.SoapBubble
import gab.opencv.OpenCV
import processing.core.PApplet
import processing.core.PImage
import processing.video.Capture
import java.util.*

class CameraHandler(
        var opencv: OpenCV,
        var camera: Capture,
        var pApplet: PApplet,
        var diffFrameMode: Boolean = false,
        var backgroundMode: Boolean = false,
        var transparentDiffMode: Boolean = false,
        var acceptableCover: Double = 0.01
) {

    var output = opencv.output
    var cameraBackground: PImage = PImage()
    var background = pApplet.loadImage("img/lab05/garden.jpg")!!

    fun setup() {
        camera.start()
        camera.read()
        opencv.startBackgroundSubtraction(1, 3, 0.5)
        saveCameraBackground()
    }

    fun draw() {
        opencv.loadImage(camera)
        opencv.flip(OpenCV.HORIZONTAL)
        opencv.updateBackground()
        //opencv.calculateOpticalFlow()
        opencv.dilate()
        opencv.erode()
        if (diffFrameMode) {
            drawDiffFrame()
        } else {
            drawImageFromCamera()
        }
    }

    fun getTouchedBubbles(bubbles: ArrayList<SoapBubble>): List<SoapBubble> {
        val bubblesPixelsMap = HashMap(bubbles.associateBy({ it }, { 0 }))
        output = opencv.output
        output.loadPixels()
        for (x in 1..output.width) {
            for (y in 1..output.height) {
                if (pApplet.brightness(output.get(x, y)) >= 255) {
                    bubbles.filter { it.containsPixel(x, y) }.forEach { bubblesPixelsMap.put(it, bubblesPixelsMap[it]?.plus(1)) }
                }
            }
        }

        return bubbles.filter { bubblesPixelsMap[it]!!.toDouble() / it.getNumberOfPixels().toDouble() > acceptableCover }
    }

    fun saveCameraBackground() {
        cameraBackground = getFlipVerticalImage(camera.get())
    }

    private fun drawDiffFrame() {
        pApplet.image(opencv.output, 0f, 0f)
    }

    private fun drawImageFromCamera() {
        val flipVerticalImage = getFlipVerticalImage(camera.get())
        if (backgroundMode) {
            drawImageWithBackground(flipVerticalImage)
        } else {
            if (transparentDiffMode) {
                drawDiffInImage(flipVerticalImage)
            }
            pApplet.image(flipVerticalImage, 0f, 0f)
        }

    }

    private fun drawDiffInImage(flipVerticalImage: PImage) {
        for (x in 1..output.width) {
            for (y in 1..output.height) {
                if (pApplet.brightness(output.get(x, y)) >= 255) {
                    flipVerticalImage.set(x, y, output.get(x, y))
                }
            }
        }
    }

    private fun drawImageWithBackground(flipVerticalImage: PImage) {
        val imageWithBackground = background.copy()
        for (x in 1..flipVerticalImage.width) {
            for (y in 1..flipVerticalImage.height) {
                val fgColor = flipVerticalImage.get(x, y)
                val bgColor = cameraBackground.get(x, y)
                val r1 = pApplet.red(fgColor)
                val g1 = pApplet.green(fgColor)
                val b1 = pApplet.blue(fgColor)
                val r2 = pApplet.red(bgColor)
                val g2 = pApplet.green(bgColor)
                val b2 = pApplet.blue(bgColor)
                val diff = PApplet.dist(r1, g1, b1, r2, g2, b2)
                if (diff > 40) {
                    imageWithBackground.set(x, y, fgColor)
                }
            }
        }
        pApplet.image(imageWithBackground, 0f, 0f)
    }

    private fun getFlipVerticalImage(original: PImage): PImage {
        val verticalImage = PImage(original.width, original.height)
        for (w in 0..original.width) {
            for (h in 0..original.height) {
                val orgColor = original.get(w, h)
                verticalImage.set(original.width - w, h, orgColor)
            }
        }
        return verticalImage
    }


}