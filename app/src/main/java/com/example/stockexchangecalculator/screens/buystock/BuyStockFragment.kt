package com.example.stockexchangecalculator.screens.buystock

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.stockexchangecalculator.R
import com.example.stockexchangecalculator.databinding.FragmentBuyStockBinding
import com.example.stockexchangecalculator.utils.CurrentUser
import com.example.stockexchangecalculator.utils.Utils.currencyFormat
import com.example.stockexchangecalculator.utils.Utils.errorSnackBar
import com.example.stockexchangecalculator.utils.Utils.toString
import yahoofinance.Stock

class BuyStockFragment : Fragment(), BuyStockContract.View {

    private lateinit var binding: FragmentBuyStockBinding
    private lateinit var stockSymbol: String
    private lateinit var buyStockPresenter: BuyStockPresenter
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
    private lateinit var stockAmountEditText: EditText
    private lateinit var resultPriceTextView: TextView
    private lateinit var buyStockButton: Button

    private var totalPrice = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_buy_stock, container, false
        )
        buyStockPresenter = BuyStockPresenter(this)
        stockSymbol = arguments?.getString("stock").toString()

        buyStockPresenter.findStockInfo(stockSymbol)

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
        stockAmountEditText = binding.stockAmount
        resultPriceTextView = binding.stockPriceResult
        buyStockButton = binding.buyStockButton


        buyStockButton.setOnClickListener { view ->
            buyStockPresenter.buyStock(
                stockAmountEditText.text.toString().toInt(),
                stockPriceTextView.text.toString().toDouble(),
                stockSymbolTextView.text.toString(),
                totalPrice
            )
            view.findNavController().navigate(R.id.action_buyStockFragment_to_portfolioFragment)
        }

        stockAmountEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isBlank()) {
                    resultPriceTextView.text = ""
                    buyStockButton.isEnabled = false
                    return
                }
                onPriceChanged()
            }
        })

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

    override fun onPriceChanged() {
        val stockPrice = buyStockPresenter.calculatePrice(
            stockPriceTextView.text.toString().toDouble(),
            stockAmountEditText.text.toString().toInt()
        )
        val tax = stockPrice * 0.03
        totalPrice = stockPrice + tax
        resultPriceTextView.text = getString(
            R.string.stock_price_result,
            currencyFormat(tax),
            currencyFormat(stockPrice),
            currencyFormat(totalPrice)
        )
        if (CurrentUser.currentUser.userMoney < totalPrice) {
            resultPriceTextView.setTextColor(Color.RED)
            buyStockButton.isEnabled = false
        } else {
            resultPriceTextView.setTextColor(Color.GREEN)
            buyStockButton.isEnabled = true
        }
    }
}