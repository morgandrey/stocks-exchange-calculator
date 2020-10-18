package com.example.stockexchangecalculator.screens.portfolio

import com.example.stockexchangecalculator.data.models.Stock
import com.example.stockexchangecalculator.utils.CurrentUser
import io.realm.Realm
import kotlinx.coroutines.*
import yahoofinance.YahooFinance

class PortfolioPresenter(private val view: PortfolioContract.View) : PortfolioContract.Presenter {

    private val job = Job()
    private val scopeMainThread = CoroutineScope(job + Dispatchers.Main)
    private val scopeIO = CoroutineScope(job + Dispatchers.IO)
    private var realm: Realm = Realm.getDefaultInstance()

    override fun deleteAllMyStocks() {
        realm.executeTransaction {
            CurrentUser.currentUser.stocks.deleteAllFromRealm()
        }
        realm.close()
        initDataset()
    }

    override fun initDataset() {
        val stockList = mutableListOf<Stock>()
        val userStocks = realm.copyFromRealm(CurrentUser.currentUser.stocks)
        val handler = CoroutineExceptionHandler { _, exception ->
            view.onNetworkError(exception.toString())
        }
        scopeIO.launch(handler) {
            for (stock in userStocks) {
                val stockYahoo = YahooFinance.get(stock.stockSymbol)
                val newStock = Stock()
                with(newStock) {
                    stockSymbol = stock.stockSymbol
                    stockChange = stockYahoo.quote.change.toDouble()
                    numberOfStocks = stock.numberOfStocks
                    averagePrice = stock.averagePrice
                }
                stockList.add(newStock)
            }
            scopeMainThread.launch {
                view.setupDataset(stockList)
            }
        }
    }
}