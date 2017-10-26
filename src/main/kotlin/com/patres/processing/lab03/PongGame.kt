package com.patres.processing.lab03

import com.patres.processing.lab03.model.Board
import com.patres.processing.lab03.model.PingBallModel
import com.patres.processing.lab03.model.Player
import processing.core.PApplet
import processing.core.PConstants.CENTER
import processing.core.PFont

class PongGame(var pApplet: PApplet, var paddleSpeed: Float, var numberOfBalls: Int, sizeX: Int, sizeY: Int) {

    var board: Board = Board(pongGame = this, sizeX = sizeX, sizeY = sizeY, borderWidth = SketchLab03.BORDER_SIZE)
    var player1: Player = Player(pongGame = this, paddleSpeed = paddleSpeed, up = true, name = "Player 1", computer = true)
    var player2: Player = Player(pongGame = this, paddleSpeed = paddleSpeed, up = false, name = "Player 2")

    var balls: ArrayList<PingBallModel> = ArrayList()

    var font: PFont = pApplet.loadFont("fonts/OCRAExtended-30.vlw")

    init {
        for (i in 1..numberOfBalls) balls.add(PingBallModel(pongGame = this))
    }

    var start = System.currentTimeMillis()

    var pause: Boolean = false

    fun draw() {

        board.draw()
        balls.forEach { it.draw() }
        player1.draw()
        player2.draw()
        drawTime()
        if (pause) drawStatus()
    }

    private fun drawStatus() {
        val message: String = when {
            player1.numberOfLife < 0 -> "${player2.name} won!"
            player2.numberOfLife < 0 -> "${player1.name} won!"
            else -> "${player1.name} - ${player2.name} \n ${player1.numberOfLife} : ${player2.numberOfLife}"
        }
        pApplet.textFont(font,32f)
        pApplet.textAlign(CENTER)
        pApplet.text(message, board.sizeX / 2f, board.sizeY * 0.7f)

    }

    private fun drawTime() {
        val time = (System.currentTimeMillis() - start) / 1000f
        pApplet.textSize(10f)
        pApplet.textAlign(CENTER)
        pApplet.text("$time s", board.sizeX / 2f, board.borderWidth + 10)
    }

}