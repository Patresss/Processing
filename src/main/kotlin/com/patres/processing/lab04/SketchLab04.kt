package com.patres.processing.lab04

import processing.core.PApplet
import processing.core.PImage

class SketchLab04 : PApplet() {

    private lateinit var original: PImage
    private lateinit var imageFilter: ImageFilter
    private lateinit var grayImage: PImage
    private lateinit var negativeImage: PImage
    private lateinit var binaryImage: PImage
    private lateinit var flipVerticalImageImage: PImage
    private lateinit var flipHorizontalImage: PImage
    private lateinit var mirrorImage: PImage

    private var images: MutableList<PImage> = ArrayList()
    private var currentImages: PImage? = null
    private var listIterator: MutableListIterator<PImage> = images.listIterator()


    override fun settings() {
        original = loadImage("img/lab04/Lenna.png")
        size(original.width, original.height)
        imageFilter = ImageFilter(original, this)

        grayImage = imageFilter.getGrayImage()
        negativeImage = imageFilter.getNegativeImage()
        binaryImage = imageFilter.getBinaryImage(min = 100, max = 200)
        flipVerticalImageImage = imageFilter.getFlipVerticalImage()
        flipHorizontalImage = imageFilter.getFlipHorizontalImage()
        mirrorImage = imageFilter.getMirrorImage()

        images = mutableListOf(original, grayImage, negativeImage, binaryImage, flipVerticalImageImage, flipHorizontalImage, mirrorImage)
        listIterator = images.listIterator()
    }

    override fun setup() {
        currentImages = listIterator.next()
    }

    override fun draw() {
        currentImages?.let {
            image(currentImages, 0f, 0f)
        }
    }

    override fun keyPressed() {
        if (key == ' ') {
            currentImages = getNextImage()
        }
    }

    private fun getNextImage(): PImage {
        if (!listIterator.hasNext()) {
            listIterator = images.listIterator()
        }
        return listIterator.next()
    }

}