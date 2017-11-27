package com.patres.processing.lab06

import com.patres.processing.fill
import gab.opencv.OpenCV
import processing.core.PApplet
import processing.video.Capture
import java.awt.Color


class SketchLab06 : PApplet() {

    companion object {
        val SCALE = 2f
        val CAMERA_RESOLUTION_WIDTH = 640
        val CAMERA_RESOLUTION_HEIGHT = 480
    }

    private lateinit var cameraHandler: CameraHandler
    private lateinit var manager: InsectManager

    override fun settings() {
        size((CAMERA_RESOLUTION_WIDTH * SCALE).toInt(), (CAMERA_RESOLUTION_HEIGHT * SCALE).toInt())
        manager = InsectManager(this)
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
        drawInformation()
        if (frameCount > 30) {
            manager.draw()
            killTouchedInsects()
        }
    }

    override fun keyPressed() {
        when (key) {
            ' ' -> cameraHandler.transparentDiffMode = !cameraHandler.transparentDiffMode
            'b' -> cameraHandler.backgroundMode = !cameraHandler.backgroundMode
            's' -> cameraHandler.saveCameraBackground()
        }
    }

    private fun killTouchedInsects() {
        val touchedInsects = cameraHandler.getTouchedInsect(manager.insects)
        touchedInsects.forEach { it.alive = false }
    }

    private fun drawInformation() {
        fill(Color.WHITE)
        text("Frame: $frameRate", 10f, 20f)
        text("Number of insects: ${manager.insects.size}", 10f, 40f)
    }

    fun captureEvent(c: Capture) {
        c.read()
    }

}