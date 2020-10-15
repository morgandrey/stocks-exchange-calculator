package com.example.stockexchangecalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stockexchangecalculator.data.models.Stock
import com.example.stockexchangecalculator.data.models.UserStock
import com.example.stockexchangecalculator.databinding.FragmentTitleBinding
import com.example.stockexchangecalculator.screens.stocks.StockAdapter
import io.realm.Realm
import io.realm.kotlin.where

class TitleFragment : Fragment() {

    private lateinit var realm: Realm
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(
            inflater,
            R.layout.fragment_title, container, false
        )

        realm = Realm.getDefaultInstance()
        recyclerView = binding.stockRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.addNewStocksButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_titleFragment_to_stockFragment)
        }
        binding.deleteStocksButton.setOnClickListener {
            deleteStocks()
        }

        initDataset()
        return binding.root
    }

    private fun deleteStocks() {
        val stocks = realm.where<Stock>()
    }

    private fun initDataset() {
        val stocks= realm.where<Stock>().findAll().toMutableList()
        recyclerView.adapter = StockAdapter(stocks)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}