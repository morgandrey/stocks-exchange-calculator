package com.example.stockexchangecalculator.screens.stocks

import com.example.stockexchangecalculator.data.models.Stock

interface StockContract {
    interface View {
        fun setupStockAdapter(dataset: MutableList<Stock>)
    }

    interface Presenter {
        fun initDataset(symbolArray: Array<String>)
    }
}