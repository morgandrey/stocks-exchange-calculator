package com.example.stockexchangecalculator.screens.portfolio

import com.example.stockexchangecalculator.data.models.Stock

interface PortfolioContract {
    interface View {
        fun setupDataset()
    }

    interface Presenter {
        fun deleteAllMyStocks()
        fun initDataset(): MutableList<Stock>
    }
}