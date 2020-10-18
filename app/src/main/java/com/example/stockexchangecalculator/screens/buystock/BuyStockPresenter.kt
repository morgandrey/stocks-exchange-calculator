package com.example.stockexchangecalculator.screens.buystock

import com.example.stockexchangecalculator.data.models.Stock
import com.example.stockexchangecalculator.utils.CurrentUser
import com.example.stockexchangecalculator.utils.Utils.roundOffDecimal
import io.realm.Realm
import kotlinx.coroutines.*
import yahoofinance.YahooFinance

class BuyStockPresenter(private val view: BuyStockContract.View) :
    BuyStockContract.Presenter {

    private val job = Job()
    private val scopeMainThread = CoroutineScope(job + Dispatchers.Main)
    private val scopeIO = CoroutineScope(job + Dispatchers.IO)
    private val realm = Realm.getDefaultInstance()

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

    override fun calculatePrice(stockPrice: Double, amountOfStocks: Int): Double =
        stockPrice * amountOfStocks

    override fun buyStock(
        amountOfStocks: Int,
        stockPrice: Double,
        stockSymbol: String,
        totalPrice: Double
    ) {
        val price = roundOffDecimal(calculatePrice(stockPrice, amountOfStocks))
        for (stock in CurrentUser.currentUser.stocks) {
            if (stock.stockSymbol == stockSymbol) {
                realm.executeTransaction {
                    val totalTotalPrice = price + stock.averagePrice * stock.numberOfStocks
                    CurrentUser.currentUser.userMoney -= totalPrice
                    stock.numberOfStocks += amountOfStocks
                    stock.averagePrice = roundOffDecimal(totalTotalPrice / stock.numberOfStocks)
                    realm.copyToRealmOrUpdate(CurrentUser.currentUser)
                }
                return
            }
        }
        val stock = Stock()
        with(stock) {
            averagePrice = stockPrice
            numberOfStocks = amountOfStocks
            this.stockSymbol = stockSymbol
        }

        realm.executeTransaction {
            CurrentUser.currentUser.userMoney -= totalPrice
            CurrentUser.currentUser.stocks.add(stock)
            realm.copyToRealmOrUpdate(CurrentUser.currentUser)
        }
    }
}