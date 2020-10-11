package com.example.stockexchangecalculator.screens.dividend

import com.example.stockexchangecalculator.screens.DoubleMath

class DividendPresenter(view: DividendContract.View) : DividendContract.Presenter {

    private var view: DividendContract.View? = view

    override fun calculateDividends(amountOfDividends: Double, amountOfStocks: Int): String =
        "Итог (с учетом комиссии): ${(DoubleMath.roundOffDecimal(amountOfDividends * amountOfStocks * 0.87))}"
}