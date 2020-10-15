package com.example.stockexchangecalculator.screens.stocks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stockexchangecalculator.R
import com.example.stockexchangecalculator.data.models.Stock
import com.example.stockexchangecalculator.databinding.FragmentStockBinding
import io.realm.Realm
import java.io.BufferedReader
import java.io.InputStreamReader

class StockFragment : Fragment(), StockContract.View {

    private lateinit var recyclerView: RecyclerView
    private lateinit var stickerSearchView: SearchView
    private lateinit var adapter: StockAdapter
    private lateinit var realm: Realm
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
        stickerSearchView = binding.searchView

        stickerSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()
    }

    override fun onStart() {
        super.onStart()
        stockPresenter.initDataset(symbolArray)
    }

    override fun onResume() {
        try {
            super.onResume()
        } catch (e: Exception) {
            print(e.message)
        }
    }

    private fun createSymbolArray(): Array<String> {
        val p = resources.openRawResource(R.raw.symbols)
        val reader = BufferedReader(InputStreamReader(p))
        var line = reader.readLine()
        val listOfSymbols = mutableListOf<String>()
        while (line != null) {
            listOfSymbols.add(line)
            line = reader.readLine()
        }
        return listOfSymbols.toTypedArray()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    override fun setupStockAdapter(dataset: MutableList<Stock>) {
        adapter = StockAdapter(dataset)
        recyclerView.adapter = adapter
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }
}