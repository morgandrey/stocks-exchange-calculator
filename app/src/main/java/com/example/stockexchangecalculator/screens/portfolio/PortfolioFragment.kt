package com.example.stockexchangecalculator.screens.portfolio

import android.app.AlertDialog
import android.content.DialogInterface
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
import com.example.stockexchangecalculator.databinding.FragmentPortfolioBinding
import com.example.stockexchangecalculator.adapters.FindStocksAdapter

class PortfolioFragment : Fragment(), PortfolioContract.View {

    private lateinit var recyclerView: RecyclerView
    private lateinit var myStocksPresenter: PortfolioPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPortfolioBinding>(
            inflater,
            R.layout.fragment_portfolio, container, false
        )
        myStocksPresenter = PortfolioPresenter(this)
        recyclerView = binding.stockRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)

        binding.addNewStocksButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_portfolioFragment_to_findStocksFragment)
        }

        binding.deleteStocksButton.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
            dialog.setCancelable(true)
            dialog.setTitle("Удаление")
            dialog.setMessage("Вы действительно хотите удалить все акции?")
            dialog.setNegativeButton ("Cancel") {
                    dialogInterface: DialogInterface, _: Int ->
                dialogInterface.cancel()
            }
            dialog.setPositiveButton("Ok") {
                    _: DialogInterface, _: Int ->
                myStocksPresenter.deleteAllMyStocks()
                setupDataset()
            }
            dialog.show()
        }

        setupDataset()
        return binding.root
    }

    override fun setupDataset() {
        recyclerView.adapter = FindStocksAdapter(myStocksPresenter.initDataset())
    }
}