package com.example.stockexchangecalculator.utils

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

object Utils {
    fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }

    fun BigDecimal.toString(numOfDec: Int): String {
        if (numOfDec < 0) {
            return this.toString()
        }
        return String.format(("%.${numOfDec}f").format(this))
    }

    fun errorSnackBar(view: View) {
        val snackBar = Snackbar.make(
            view, "Network error!",
            Snackbar.LENGTH_LONG
        )
        snackBar.setTextColor(Color.WHITE)
        snackBar.setBackgroundTint(Color.RED)
        snackBar.show()
    }
}