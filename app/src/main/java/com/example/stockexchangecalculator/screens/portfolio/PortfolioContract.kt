package com.example.stockexchangecalculator.screens.portfolio

import com.example.stockexchangecalculator.data.models.Stock

interface MyStocksContract {
    interface View {
        fun setupDataset()
    }

    interface Presenter {
        fun deleteAllMyStocks()
        fun initDataset(): MutableList<Stock>
    }
}