package com.example.wifiscanner.utils

import java.lang.Math.pow
import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.roundToInt

fun dampingInFreeSpace(frequency: Int, level: Int): Int {
    val exp = (27.55 - (20 * log10(frequency.toDouble())) + abs(level)) / 20
    val d = pow(10.0, exp)

    return d.roundToInt()
}
