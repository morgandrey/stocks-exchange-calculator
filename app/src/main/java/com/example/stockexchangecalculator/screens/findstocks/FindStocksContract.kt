package com.example.stockexchangecalculator.screens.findstocks

import com.example.stockexchangecalculator.data.models.Stock

interface FindStocksContract {
    interface View {
        fun setupStockAdapter(dataset: MutableList<Stock>)
        fun onNetworkError()
    }

    interface Presenter {
        fun initDataset(symbolArray: Array<String>)
    }
}