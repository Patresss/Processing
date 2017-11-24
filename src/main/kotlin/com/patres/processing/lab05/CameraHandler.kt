package com.patres.processing.lab05

import com.patres.processing.flipVerticalImage
import com.patres.processing.lab05.model.SoapBubble
import gab.opencv.OpenCV
import processing.core.PApplet
import processing.core.PImage
import processing.video.Capture
import java.util.*

class CameraHandler(
        var openCv: OpenCV,
        var camera: Capture,
        var pApplet: PApplet,
        var backgroundMode: Boolean = false,
        var transparentDiffMode: Boolean = false,
        private var acceptableCover: Double = 0.01
) {

    private var output = openCv.output.flipVerticalImage()
    private var cameraBackground: PImage = PImage()
    private var background = pApplet.loadImage("img/lab05/garden.jpg")!!

    fun setup() {
        camera.start()
        camera.read()
        openCv.startBackgroundSubtraction(1, 3, 0.5)
        saveCameraBackground()
    }

    fun draw() {
        openCv.loadImage(camera)
        openCv.updateBackground()
        openCv.dilate()
        openCv.erode()
        output = openCv.output.flipVerticalImage()
        drawImageFromCamera()
    }

    fun getTouchedBubbles(bubbles: ArrayList<SoapBubble>): List<SoapBubble> {
        val bubblesPixelsMap = HashMap(bubbles.associateBy({ it }, { 0 }))
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
        cameraBackground = camera.get().flipVerticalImage()
    }

    private fun drawImageFromCamera() {
        val cameraImage = camera.get().flipVerticalImage()
        if (backgroundMode) {
            drawImageWithBackground(cameraImage)
            addTransparentDiff(cameraImage)
        } else {
            addTransparentDiff(cameraImage)
            pApplet.image(cameraImage, 0f, 0f, SketchLab05.CAMERA_RESOLUTION_WIDTH * SketchLab05.SCALE, SketchLab05.CAMERA_RESOLUTION_HEIGHT * SketchLab05.SCALE)
        }

    }

    private fun addTransparentDiff(image: PImage) {
        if (transparentDiffMode) {
            drawDiffInImage(image)
        }
    }

    private fun drawDiffInImage(image: PImage) {
        for (x in 1..output.width) {
            for (y in 1..output.height) {
                if (pApplet.brightness(output.get(x, y)) >= 100) {
                    image.set(x, y, output.get(x, y))
                }
            }
        }
    }

    private fun drawImageWithBackground(image: PImage) {
        val imageWithBackground = background.copy()
        for (x in 1..image.width) {
            for (y in 1..image.height) {
                val foregroundColor = image.get(x, y)
                val cameraBackgroundColor = cameraBackground.get(x, y)
                val imageBackground = imageWithBackground.get(x, y)
                val r1 = pApplet.red(foregroundColor)
                val g1 = pApplet.green(foregroundColor)
                val b1 = pApplet.blue(foregroundColor)
                val r2 = pApplet.red(cameraBackgroundColor)
                val g2 = pApplet.green(cameraBackgroundColor)
                val b2 = pApplet.blue(cameraBackgroundColor)
                val diff = PApplet.dist(r1, g1, b1, r2, g2, b2)
                if (diff < 30) {
                    image.set(x, y, imageBackground)
                }
            }
        }
        pApplet.image(image, 0f, 0f, SketchLab05.CAMERA_RESOLUTION_WIDTH * SketchLab05.SCALE, SketchLab05.CAMERA_RESOLUTION_HEIGHT * SketchLab05.SCALE)
    }

}