package com.patres.processing.utils

import java.awt.Color
import java.util.*


class RandomGenerator {
    companion object {
        var random = Random()
        fun generateFloat(min: Float, max: Float): Float {
            return random.nextFloat() * (max - min) + min
        }
        fun generateColor(): Color {
            return Color(random.nextInt(256*256*256))
        }
    }
}

