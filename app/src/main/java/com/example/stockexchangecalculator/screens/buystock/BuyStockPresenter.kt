package com.example.stockexchangecalculator.screens.stockdetails

import kotlinx.coroutines.*
import yahoofinance.YahooFinance

class StockDetailsPresenter(private val view: StockDetailsContract.View) :
    StockDetailsContract.Presenter {

    private val job = Job()
    private val scopeMainThread = CoroutineScope(job + Dispatchers.Main)
    private val scopeIO = CoroutineScope(job + Dispatchers.IO)

    override fun findStockInfo(stockSymbol: String) {
        val handler = CoroutineExceptionHandler { _, _ ->
            view.onNetworkError()
        }
        scopeIO.launch(handler) {
            val stock = YahooFinance.get(stockSymbol)
            scopeMainThread.launch {
                view.setupStockInfo(stock)
            }
        }
    }

}