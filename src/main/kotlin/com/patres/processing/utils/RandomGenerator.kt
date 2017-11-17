package com.patres.processing.utils

import processing.core.PVector
import java.awt.Color
import java.util.*


class RandomGenerator {
    companion object {
        var random = Random()
        fun generateFloat(min: Float = 0f, max: Float): Float = random.nextFloat() * (max - min) + min
        fun generateColor(): Color = Color(random.nextInt(256*256*256))
        fun generateVector(min: Float = 0f, max: Float): PVector = PVector(generateFloat(min, max), generateFloat(min, max))
    }
}

