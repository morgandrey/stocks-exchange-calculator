package com.example.stockexchangecalculator.screens.stockreturns

interface StockReturnsContract {
    interface View {
        fun onError()
    }

    interface Presenter {
        fun calculate(
            averagePrice: Double,
            amountOfStocks: Int,
            currentPrice: Double,
            tax: Double
        ): String
    }
}