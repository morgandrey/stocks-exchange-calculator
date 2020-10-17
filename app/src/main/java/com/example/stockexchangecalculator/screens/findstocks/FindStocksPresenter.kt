package com.example.stockexchangecalculator.screens.findstocks

import com.example.stockexchangecalculator.data.models.Stock
import kotlinx.coroutines.*
import yahoofinance.YahooFinance


class FindStocksPresenter(private val view: FindStocksContract.View) :
    FindStocksContract.Presenter {

    private val job = Job()
    private val scopeMainThread = CoroutineScope(job + Dispatchers.Main)
    private val scopeIO = CoroutineScope(job + Dispatchers.IO)

    override fun initDataset(symbolArray: Array<String>) {
        val dataset = mutableListOf<Stock>()
        val handler = CoroutineExceptionHandler { _, _ ->
            view.onNetworkError()
        }
        scopeIO.launch(handler) {
            supervisorScope {
                for (symbol in symbolArray) {
                    val stock = YahooFinance.get(symbol) ?: continue

                    if (stock.symbol == null
                        || stock.quote.price == null
                        || stock.quote.changeInPercent == null
                    ) {
                        continue
                    }
                    val newStock = Stock(
                        stockSymbol = stock.symbol,
                        stockName = stock.name,
                        stockPrice = stock.quote.price.toDouble(),
                        stockChange = stock.quote.change.toDouble()
                    )
                    dataset.add(newStock)
                }

                scopeMainThread.launch {
                    view.setupStockAdapter(dataset)
                }
            }
        }
    }
}
