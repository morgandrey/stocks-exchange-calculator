package com.example.stockexchangecalculator.screens

import java.math.RoundingMode
import java.text.DecimalFormat

object DoubleMath {
    fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }
}