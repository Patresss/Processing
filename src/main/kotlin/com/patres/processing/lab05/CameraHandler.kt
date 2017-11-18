package com.patres.processing.lab05

import com.patres.processing.lab05.model.SoapBubble
import gab.opencv.OpenCV
import processing.core.PApplet
import processing.video.Capture
import java.util.*

class CameraHandler(
        var opencv: OpenCV,
        var camera: Capture,
        var pApplet: PApplet,
        var scale: Float = 1f,
        var diffFrameMode: Boolean = false,
        var acceptableCover: Double = 0.05
) {

    val output = opencv.output

    fun setup() {
        camera.start()
        camera.read()
        opencv.startBackgroundSubtraction(1, 3, 0.5)
    }

    fun draw() {
        pApplet.scale(scale)
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

    private fun drawDiffFrame() {
        pApplet.image(opencv.output, 0f, 0f)
    }

    private fun drawImageFromCamera() {
        pApplet.pushMatrix()
        pApplet.scale(-1f, 1f)
        pApplet.image(camera.get(), -pApplet.width.toFloat() / scale, 0f)
        pApplet.popMatrix()
    }

    fun getTouchedBubbles(bubbles: ArrayList<SoapBubble>): List<SoapBubble> {
        val bubblesPixelsMap = HashMap(bubbles.associateBy({ it }, { 0 }))
        val output = opencv.output
        output.loadPixels()
        for (x in 1..output.width) {
            for (y in 1..output.height) {
                if (pApplet.brightness(output.get(x, y)) >= 255) {
                    bubbles.filter { it.containsPixel(x, y) }.forEach { bubblesPixelsMap.put(it, bubblesPixelsMap[it]?.plus(1)) }
                }
            }
        }

        return bubbles.filter { bubblesPixelsMap[it]!!.toDouble() / it.getNumberOfPixels().toDouble() > acceptableCover}
    }

}