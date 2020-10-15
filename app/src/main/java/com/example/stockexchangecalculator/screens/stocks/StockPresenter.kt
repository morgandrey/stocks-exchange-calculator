package com.example.stockexchangecalculator.screens.stocks

import android.content.Context
import android.widget.Toast
import com.example.stockexchangecalculator.R
import com.example.stockexchangecalculator.data.models.Stock
import kotlinx.coroutines.*
import yahoofinance.YahooFinance
import java.io.File
import java.io.IOException
import java.io.InputStream


class StockPresenter(private val view: StockContract.View) : StockContract.Presenter {

    private val job = Job()
    private val scopeMainThread = CoroutineScope(job + Dispatchers.Main)
    private val scopeIO = CoroutineScope(job + Dispatchers.IO)

    override fun initDataset(symbolArray: Array<String>) {
        val dataset = mutableListOf<Stock>()
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }
        scopeIO.launch(handler) {
            supervisorScope {
                for (symbol in symbolArray) {
                    val stock = YahooFinance.get(symbol) ?: continue
                    print(stock.name)
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
