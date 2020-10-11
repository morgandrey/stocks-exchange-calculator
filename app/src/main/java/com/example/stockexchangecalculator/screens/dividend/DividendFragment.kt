package com.example.stockexchangecalculator.screens.dividend

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
import com.example.stockexchangecalculator.databinding.FragmentDividendBinding

class DividendFragment : Fragment(), DividendContract.View {

    private lateinit var binding: FragmentDividendBinding
    private lateinit var presenter: DividendPresenter
    private lateinit var amountOfDividendsEditText: EditText
    private lateinit var numberOfStocksEditText: EditText
    private lateinit var dividendsTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_dividend, container, false
        )
        presenter = DividendPresenter(this)
        amountOfDividendsEditText = binding.amountOfDividendsEditText
        numberOfStocksEditText = binding.numberOfStocksEditText
        dividendsTextView = binding.dividendsTextView
        binding.calculateDividendsButton.setOnClickListener { calculateDividends() }
        return binding.root
    }

    private fun calculateDividends() {
        try {
            binding.dividendsTextView.text = presenter.calculateDividends(
                amountOfDividendsEditText.text.toString().toDouble(),
                numberOfStocksEditText.text.toString().toInt()
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