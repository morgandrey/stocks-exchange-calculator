package com.example.stockexchangecalculator.screens.stockdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.stockexchangecalculator.R
import com.example.stockexchangecalculator.databinding.FragmentStockDetailsBinding
import com.example.stockexchangecalculator.utils.Utils.errorSnackBar
import com.example.stockexchangecalculator.utils.Utils.toString
import yahoofinance.Stock

class StockDetailsFragment : Fragment(), StockDetailsContract.View {

    private lateinit var binding: FragmentStockDetailsBinding
    private lateinit var stockSymbol: String
    private lateinit var stockDetailsPresenter: StockDetailsPresenter
    private lateinit var stockSymbolTextView: TextView
    private lateinit var stockNameTextView: TextView
    private lateinit var stockExchangeTextView: TextView
    private lateinit var stockPriceTextView: TextView
    private lateinit var stockChangePriceTextView: TextView
    private lateinit var stockChangePricePercentTextView: TextView
    private lateinit var stockOpeningPriceTextView: TextView
    private lateinit var stockPreviousClosePriceTextView: TextView
    private lateinit var stockDayHighView: TextView
    private lateinit var stockDayLowView: TextView
    private lateinit var stockYearHighView: TextView
    private lateinit var stockYearLowView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_stock_details, container, false
        )
        stockDetailsPresenter = StockDetailsPresenter(this)
        stockSymbol = arguments?.getString("stock").toString()

        stockDetailsPresenter.findStockInfo(stockSymbol)

        stockSymbolTextView = binding.stockSymbol
        stockNameTextView = binding.stockName
        stockExchangeTextView = binding.stockExchange
        stockPriceTextView = binding.stockPrice
        stockChangePriceTextView = binding.stockChange
        stockChangePricePercentTextView = binding.stockChangeInPercent
        stockOpeningPriceTextView = binding.stockOpen
        stockPreviousClosePriceTextView = binding.stockPreviousClose
        stockDayHighView = binding.stockDayHigh
        stockDayLowView = binding.stockDayLow
        stockYearHighView = binding.stockYearHigh
        stockYearLowView = binding.stockYearLow

        return binding.root
    }

    override fun setupStockInfo(stock: Stock) {
        stockSymbolTextView.text = stock.symbol
        stockNameTextView.text = stock.name
        stockExchangeTextView.text = stock.stockExchange.toString()
        stockPriceTextView.text = stock.quote.price.toString(2)
        stockChangePriceTextView.text = stock.quote.change.toString(2)
        stockChangePricePercentTextView.text = stock.quote.changeInPercent.toString(2)
        stockOpeningPriceTextView.text = stock.quote.open.toString(2)
        stockPreviousClosePriceTextView.text = stock.quote.previousClose.toString(2)
        stockDayHighView.text = stock.quote.dayHigh.toString(2)
        stockDayLowView.text = stock.quote.dayLow.toString(2)
        stockYearHighView.text = stock.quote.yearHigh.toString(2)
        stockYearLowView.text = stock.quote.yearLow.toString(2)
    }

    override fun onNetworkError() {
        errorSnackBar(requireView())
    }
}