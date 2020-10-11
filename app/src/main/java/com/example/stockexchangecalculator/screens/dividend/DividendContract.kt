package com.example.stockexchangecalculator.screens.dividend

interface DividendContract {
    interface View {
        fun onError()
    }

    interface Presenter {
        fun calculateDividends(amountOfDividends: Double, amountOfStocks: Int): String
    }
}