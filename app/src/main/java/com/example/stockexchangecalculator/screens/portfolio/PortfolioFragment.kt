package com.example.stockexchangecalculator.screens.portfolio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stockexchangecalculator.R
import com.example.stockexchangecalculator.databinding.FragmentMyStocksBinding
import com.example.stockexchangecalculator.screens.stocks.StockAdapter

class MyStocksFragment : Fragment(), MyStocksContract.View {

    private lateinit var recyclerView: RecyclerView
    private lateinit var myStocksPresenter: MyStocksPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMyStocksBinding>(
            inflater,
            R.layout.fragment_my_stocks, container, false
        )
        myStocksPresenter = MyStocksPresenter(this)
        recyclerView = binding.stockRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)

        binding.addNewStocksButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_myStocksFragment_to_stockFragment)
        }

        binding.deleteStocksButton.setOnClickListener {
            myStocksPresenter.deleteAllMyStocks()
            setupDataset()
        }

        setupDataset()
        return binding.root
    }

    override fun setupDataset() {
        recyclerView.adapter = StockAdapter(myStocksPresenter.initDataset())
    }
}