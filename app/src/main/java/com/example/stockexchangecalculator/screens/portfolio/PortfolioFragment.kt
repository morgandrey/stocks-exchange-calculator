package com.example.stockexchangecalculator.screens.portfolio

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stockexchangecalculator.R
import com.example.stockexchangecalculator.databinding.FragmentPortfolioBinding
import com.example.stockexchangecalculator.adapters.PortfolioStockAdapter
import com.example.stockexchangecalculator.data.models.Stock
import com.example.stockexchangecalculator.utils.CurrentUser
import com.example.stockexchangecalculator.utils.Utils.currencyFormat
import com.example.stockexchangecalculator.utils.Utils.errorSnackBar

class PortfolioFragment : Fragment(), PortfolioContract.View {

    private lateinit var recyclerView: RecyclerView
    private lateinit var portfolioPresenter: PortfolioPresenter
    private lateinit var userMoneyTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPortfolioBinding>(
            inflater,
            R.layout.fragment_portfolio, container, false
        )
        portfolioPresenter = PortfolioPresenter(this)
        recyclerView = binding.stockRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        userMoneyTextView = binding.userMoneyTextView
        userMoneyTextView.text = getString(
            R.string.user_money_label,
            currencyFormat(CurrentUser.currentUser.userMoney)
        )
        binding.addNewStocksButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_portfolioFragment_to_findStocksFragment)
        }

        binding.deleteStocksButton.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
            with(dialog) {
                setCancelable(true)
                setTitle("Удаление")
                setMessage("Вы действительно хотите удалить все акции?")
                setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.cancel()
                }
                setPositiveButton("Ok") { _: DialogInterface, _: Int ->
                    portfolioPresenter.deleteAllMyStocks()
                }
                show()
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        portfolioPresenter.initDataset()
    }

    override fun setupDataset(dataset: MutableList<Stock>) {
        recyclerView.adapter = PortfolioStockAdapter(dataset)
    }

    override fun onNetworkError(exception: String) {
        errorSnackBar(requireView(), exception)
    }
}