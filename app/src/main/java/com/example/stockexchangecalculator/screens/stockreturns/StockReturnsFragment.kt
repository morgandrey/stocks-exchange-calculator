package com.example.stockexchangecalculator.screens.stockreturns

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.stockexchangecalculator.R
import com.example.stockexchangecalculator.databinding.FragmentStockReturnsBinding

class StockReturnsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentStockReturnsBinding>(
            inflater, R.layout.fragment_stock_returns, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }
    /*private fun calculate() {
        try {
            val tax = binding.taxEditText.text.toString().toDouble() * 0.01
            val numberOfStocks = binding.numberOfStocksEditText.text.toString().toInt()
            val averagePriceOfStocks = binding.averagePriceEditText.text.toString().toDouble()
            val totalPrice = roundOffDecimal(averagePriceOfStocks * numberOfStocks)
            val currentPrice = binding.currentPriceEditText.text.toString().toDouble()
            val value = roundOffDecimal(currentPrice * numberOfStocks - totalPrice)
            val taxBuy = roundOffDecimal(tax * totalPrice)
            val taxSell = roundOffDecimal(tax * (currentPrice * numberOfStocks))
            val totalValue = roundOffDecimal(value - taxBuy - taxSell)
            setTextViewsVisibility()
            setTextViewColor(binding.valueTextView, value)
            setTextViewColor(binding.totalValueTextView, totalValue)
            binding.taxBuyTextView.text = getString(R.string.tax_buy, taxBuy.toString())
            binding.taxSellTextView.text = getString(R.string.tax_sell, taxSell.toString())
            binding.totalPriceTextView.text = getString(R.string.total_price, totalPrice.toString())
            binding.valueTextView.text = getString(R.string.value_price, value.toString())
            binding.totalValueTextView.text = getString(R.string.total_value_price, totalValue.toString())
        } catch (e: Exception) {
            print(e.message)
        }
    }

    private fun setTextViewColor(textView: TextView, value: Double) {
        if (value > 0.0) {
            textView.setTextColor(ContextCompat.getColor(this, R.color.colorValue))
        } else {
            textView.setTextColor(Color.RED)
        }
    }

    private fun setTextViewsVisibility() {
        binding.taxBuyTextView.visibility = View.VISIBLE
        binding.taxSellTextView.visibility = View.VISIBLE
        binding.totalPriceTextView.visibility = View.VISIBLE
        binding.valueTextView.visibility = View.VISIBLE
        binding.totalValueTextView.visibility = View.VISIBLE
    }

    private fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }*/
}