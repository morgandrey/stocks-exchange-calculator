package com.example.stockexchangecalculator.screens.stocks

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stockexchangecalculator.R
import com.example.stockexchangecalculator.data.models.Stock
import com.example.stockexchangecalculator.databinding.FragmentStockBinding
import java.io.BufferedReader
import java.io.InputStreamReader


class StockFragment : Fragment(), StockContract.View {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StockAdapter
    private lateinit var symbolArray: Array<String>
    private lateinit var binding: FragmentStockBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var stockPresenter: StockPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_stock, container, false
        )
        adapter = StockAdapter(mutableListOf())
        recyclerView = binding.recycleStockView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        symbolArray = createSymbolArray()
        stockPresenter = StockPresenter(this)
        recyclerView.setHasFixedSize(true)

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
        adapter = StockAdapter(dataset)
        recyclerView.adapter = adapter
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }
}