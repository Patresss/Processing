package com.patres.processing.lab05

import com.patres.processing.fill
import gab.opencv.OpenCV
import processing.core.PApplet
import processing.core.PConstants
import processing.video.Capture
import java.awt.Color


class SketchLab05 : PApplet() {

    companion object {
        val SCALE = 1f
        val CAMERA_RESOLUTION_WIDTH = 640
        val CAMERA_RESOLUTION_HEIGHT = 480
    }

    private lateinit var cameraHandler: CameraHandler
    private lateinit var soapBubbleManager: SoapBubbleManager

    override fun settings() {
        size((CAMERA_RESOLUTION_WIDTH * SCALE).toInt(), (CAMERA_RESOLUTION_HEIGHT * SCALE).toInt())
        soapBubbleManager = SoapBubbleManager(this)
        val openCv = OpenCV(this, CAMERA_RESOLUTION_WIDTH, CAMERA_RESOLUTION_HEIGHT)
        val camera = Capture(this, CAMERA_RESOLUTION_WIDTH, CAMERA_RESOLUTION_HEIGHT)
        cameraHandler = CameraHandler(pApplet = this, openCv = openCv, camera = camera)
    }

    override fun setup() {
        background(255)
        cameraHandler.setup()
    }

    override fun draw() {
        cameraHandler.draw()
        soapBubbleManager.draw()
        removeTouchedBubbles()
        drawInformation()
    }

    override fun keyPressed() {
        when (key) {
            ' ' -> cameraHandler.transparentDiffMode = !cameraHandler.transparentDiffMode
            'b' -> cameraHandler.backgroundMode = !cameraHandler.backgroundMode
            's' -> cameraHandler.saveCameraBackground()
        }

        when (keyCode) {
            PConstants.UP -> soapBubbleManager.speedY += 0.1f
            PConstants.DOWN -> soapBubbleManager.speedY -= 0.1f
            PConstants.LEFT -> soapBubbleManager.frequencyOfNewBubbles -= 100
            PConstants.RIGHT -> soapBubbleManager.frequencyOfNewBubbles += 100
        }

    }

    private fun removeTouchedBubbles() {
        val touchedBubbles = cameraHandler.getTouchedBubbles(soapBubbleManager.bubbles)
        soapBubbleManager.removeLife(touchedBubbles.filter { !it.touched })
        soapBubbleManager.bubbles.forEach { it.touched = touchedBubbles.contains(it) }
    }

    private fun drawInformation() {
        fill(Color.WHITE)
        text("Frame: $frameRate", 10f, 20f)
        text("Number of bubbles: ${soapBubbleManager.bubbles.size}", 10f, 40f)
        text("Frequency of new bubbles: ${soapBubbleManager.frequencyOfNewBubbles / 1000f} s", 10f, 60f)
    }

    fun captureEvent(c: Capture) {
        c.read()
    }

}