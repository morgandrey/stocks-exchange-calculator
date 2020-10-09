package com.example.stockexchangecalculator.screens.dividend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.stockexchangecalculator.R
import com.example.stockexchangecalculator.databinding.FragmentDividendBinding

class DividendFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentDividendBinding>(
            inflater, R.layout.fragment_dividend, container, false)
        return binding.root
    }
}