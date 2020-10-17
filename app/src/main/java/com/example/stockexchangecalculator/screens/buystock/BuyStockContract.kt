package com.example.stockexchangecalculator.screens.stockdetails

import yahoofinance.Stock

interface StockDetailsContract {
    interface View {
        fun setupStockInfo(stock: Stock)
        fun onNetworkError()
    }

    interface Presenter {
        fun findStockInfo(stockSymbol: String)
    }
}