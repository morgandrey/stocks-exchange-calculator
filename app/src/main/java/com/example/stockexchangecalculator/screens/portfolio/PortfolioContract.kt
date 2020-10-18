package com.example.stockexchangecalculator.screens.portfolio

import com.example.stockexchangecalculator.data.models.Stock

interface PortfolioContract {
    interface View {
        fun setupDataset(dataset: MutableList<Stock>)
        fun onNetworkError(exception: String)
    }

    interface Presenter {
        fun deleteAllMyStocks()
        fun initDataset()
    }
}