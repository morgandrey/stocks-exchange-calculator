package com.example.stockexchangecalculator.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.stockexchangecalculator.R
import com.example.stockexchangecalculator.databinding.ActivityMainBinding
import com.example.stockexchangecalculator.utils.Constants

class MainActivity : AppCompatActivity() {

    private var backPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
    }

    /*override fun onBackPressed() {
        if (backPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            Toast.makeText(
                applicationContext,
                "Tap back button in order to exit",
                Toast.LENGTH_SHORT
            ).show()
        }

        backPressed = System.currentTimeMillis()
    }
    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        if (count == 2 || count == 0) {
            super.onBackPressed()
            return
        } else {
            supportFragmentManager.popBackStack()
        }
    }*/
}