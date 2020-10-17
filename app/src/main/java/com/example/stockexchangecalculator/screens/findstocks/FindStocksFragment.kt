package com.example.stockexchangecalculator.screens.findstocks

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stockexchangecalculator.R
import com.example.stockexchangecalculator.adapters.FindStocksAdapter
import com.example.stockexchangecalculator.data.models.Stock
import com.example.stockexchangecalculator.databinding.FragmentFindStocksBinding
import com.example.stockexchangecalculator.utils.Utils.errorSnackBar
import java.io.BufferedReader
import java.io.InputStreamReader


class FindStocksFragment : Fragment(), FindStocksContract.View {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FindStocksAdapter
    private lateinit var symbolArray: Array<String>
    private lateinit var binding: FragmentFindStocksBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var stockPresenter: FindStocksPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_find_stocks, container, false
        )
        adapter = FindStocksAdapter(mutableListOf())

        recyclerView = binding.recycleStockView
        with(recyclerView) {
            layoutManager = LinearLayoutManager(activity)
            visibility = View.GONE
            setHasFixedSize(true)
        }
        progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE
        symbolArray = createSymbolArray()
        stockPresenter = FindStocksPresenter(this)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_stocks_item_menu, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    override fun onStart() {
        super.onStart()
        stockPresenter.initDataset(symbolArray)
    }

    private fun createSymbolArray(): Array<String> {
        val file = resources.openRawResource(R.raw.symbols)
        val reader = BufferedReader(InputStreamReader(file))
        var line = reader.readLine()
        val listOfSymbols = mutableListOf<String>()
        while (line != null) {
            listOfSymbols.add(line)
            line = reader.readLine()
        }
        return listOfSymbols.toTypedArray()
    }

    override fun setupStockAdapter(dataset: MutableList<Stock>) {
        adapter = FindStocksAdapter(dataset)
        recyclerView.adapter = adapter
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun onNetworkError() {
        errorSnackBar(requireView())
    }
}