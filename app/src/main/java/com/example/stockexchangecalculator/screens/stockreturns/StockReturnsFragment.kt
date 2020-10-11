package com.example.stockexchangecalculator.screens.stockreturns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.stockexchangecalculator.R
import com.example.stockexchangecalculator.databinding.FragmentStockReturnsBinding

class StockReturnsFragment : Fragment(), StockReturnsContract.View {

    private lateinit var binding: FragmentStockReturnsBinding
    private lateinit var taxEditText: EditText
    private lateinit var numberOfStocksEditText: EditText
    private lateinit var averagePriceEditText: EditText
    private lateinit var currentPriceEditText: EditText
    private lateinit var resultTextView: TextView

    private lateinit var presenter: StockReturnsPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_stock_returns, container, false
        )
        presenter = StockReturnsPresenter(this)
        taxEditText = binding.taxEditText
        numberOfStocksEditText = binding.numberOfStocksEditText
        averagePriceEditText = binding.averagePriceEditText
        currentPriceEditText = binding.currentPriceEditText
        resultTextView = binding.stockReturnsResultTextView

        binding.calculateButton.setOnClickListener { calculate() }
        return binding.root
    }

    private fun calculate() {
        try {
            resultTextView.text = presenter.calculate(
                averagePriceEditText.text.toString().toDouble(),
                numberOfStocksEditText.text.toString().toInt(),
                currentPriceEditText.text.toString().toDouble(),
                taxEditText.text.toString().toDouble()
            )
        } catch (e: Exception) {
            onError()
        }
    }

    override fun onError() {
        val context = context?.applicationContext ?: return
        val toast = Toast.makeText(context, "Введите значения", Toast.LENGTH_SHORT)
        toast.show()
    }
}