package com.example.stockexchangecalculator.screens.stockreturns

import com.example.stockexchangecalculator.screens.DoubleMath.roundOffDecimal

class StockReturnsPresenter(view: StockReturnsContract.View) : StockReturnsContract.Presenter {

    private var view: StockReturnsContract.View? = view

    override fun calculate(
        averagePrice: Double,
        amountOfStocks: Int,
        currentPrice: Double,
        tax: Double
    ): String {
        val totalPrice = roundOffDecimal(averagePrice * amountOfStocks)
        val value = roundOffDecimal(currentPrice * amountOfStocks - totalPrice)
        val valuePercent = roundOffDecimal(value / totalPrice * 100)
        val taxBuy = roundOffDecimal(tax * totalPrice)
        val taxSell = roundOffDecimal(tax * (currentPrice * amountOfStocks))
        val totalValue = roundOffDecimal(value - taxBuy - taxSell)
        return "Налог на покупку: $taxBuy\n" +
                "Налог на продажу: $taxSell\n" +
                "Всего акций: $totalPrice\n" +
                "Доход: $value ($valuePercent %)\n" +
                "Итог: $totalValue"
    }
}