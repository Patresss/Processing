package com.patres.processing.lab05

import com.patres.processing.fill
import gab.opencv.OpenCV
import processing.core.PApplet
import processing.video.Capture
import processing.core.PConstants
import java.awt.Color


class SketchLab05 : PApplet() {

    companion object {
        val SCALE = 1f
        val CAMERA_RESOLUTION_WIDTH = 640
        val CAMERA_RESOLUTION_HEIGHT = 480
    }

    lateinit var cameraHandler: CameraHandler
    lateinit var soapBubbleFactory: SoapBubbleFactory


    override fun settings() {
        size((CAMERA_RESOLUTION_WIDTH * SCALE).toInt(), (CAMERA_RESOLUTION_HEIGHT * SCALE).toInt())
        soapBubbleFactory = SoapBubbleFactory(this)
        val opencv = OpenCV(this, CAMERA_RESOLUTION_WIDTH, CAMERA_RESOLUTION_HEIGHT)
        val camera = Capture(this, CAMERA_RESOLUTION_WIDTH, CAMERA_RESOLUTION_HEIGHT)
        cameraHandler = CameraHandler(pApplet = this, opencv = opencv, camera = camera, scale = SCALE)
    }

    override fun setup() {
        background(255)
        ellipseMode(PConstants.RADIUS)
        cameraHandler.setup()
    }

    override fun draw() {
        cameraHandler.draw()
        soapBubbleFactory.draw()
        removeTouchedBubbles()
        drawInformation()
    }

    private fun removeTouchedBubbles() {
        val touchedBubbles = cameraHandler.getTouchedBubbles(soapBubbleFactory.bubbles)
        soapBubbleFactory.removeLife(touchedBubbles.filter { !it.touched })
        soapBubbleFactory.bubbles.filter { !touchedBubbles.contains(it) }.forEach { it.touched = false }
        soapBubbleFactory.bubbles.filter { touchedBubbles.contains(it) }.forEach { it.touched = true }

    }



    fun drawInformation() {
        fill(Color.WHITE)
        text("Frame: $frameRate", 10f, 20f)
        text("Number of bubbles: ${soapBubbleFactory.bubbles.size}", 10f, 40f)
        text("Frequency of new bubbles: ${soapBubbleFactory.frequencyOfNewBubbles / 1000f} s", 10f, 60f)
    }

    override fun keyPressed() {
        if (key == ' ') {
            cameraHandler.diffFrameMode = !cameraHandler.diffFrameMode
        }

        when (keyCode) {
            PConstants.UP -> soapBubbleFactory.speedY += 0.1f
            PConstants.DOWN -> soapBubbleFactory.speedY -= 0.1f
            PConstants.LEFT -> soapBubbleFactory.frequencyOfNewBubbles -= 100
            PConstants.RIGHT -> soapBubbleFactory.frequencyOfNewBubbles += 100
        }

    }

    fun captureEvent(c: Capture) {
        c.read()
    }


}