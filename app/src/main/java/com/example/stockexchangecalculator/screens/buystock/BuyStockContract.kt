package com.example.stockexchangecalculator.screens.buystock

import yahoofinance.Stock

interface BuyStockContract {
    interface View {
        fun setupStockInfo(stock: Stock)
        fun onNetworkError()
        fun onPriceChanged()
    }

    interface Presenter {
        fun findStockInfo(stockSymbol: String)
        fun calculatePrice(stockPrice: Double, amountOfStocks: Int): Double
        fun buyStock(
            amountOfStocks: Int,
            stockPrice: Double,
            stockSymbol: String,
            totalPrice: Double
        )
    }
}