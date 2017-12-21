package com.patres.processing.lab07

import processing.core.PApplet
import processing.core.PImage

class ImageKeeper(val pApplet: PApplet) {

    val background: PImage = pApplet.loadImage("img/lab07/background.png") ?: PImage()
    val cannonImage: PImage = pApplet.loadImage("img/lab07/cannon.png") ?: PImage()
    val snowBall: PImage = pApplet.loadImage("img/lab07/snowball.png") ?: PImage()
    val ice: PImage = pApplet.loadImage("img/lab07/ice.png") ?: PImage()
    val wonScreen: PImage = pApplet.loadImage("img/lab07/won-screen.png") ?: PImage()
}