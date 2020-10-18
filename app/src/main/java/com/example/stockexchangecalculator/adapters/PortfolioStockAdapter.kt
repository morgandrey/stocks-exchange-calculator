package com.example.stockexchangecalculator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stockexchangecalculator.R
import com.example.stockexchangecalculator.data.models.Stock
import com.example.stockexchangecalculator.utils.Utils.roundOffDecimal

class PortfolioStockAdapter(dataSet: MutableList<Stock>) :
    RecyclerView.Adapter<PortfolioStockAdapter.ViewHolder>() {

    var dataSetFiltered = mutableListOf<Stock>()

    init {
        dataSetFiltered = dataSet
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val stockSymbol: TextView = itemView.findViewById(R.id.stock_symbol)
        private val stockAmount: TextView = itemView.findViewById(R.id.number_of_stocks_text_view)
        private val stockAveragePrice: TextView =
            itemView.findViewById(R.id.average_price_text_view)
        private val stockTotalPrice: TextView = itemView.findViewById(R.id.total_price_text_view)
        private val stockChange: TextView = itemView.findViewById(R.id.change_text_view)

        fun bind(item: Stock) {
            val change = item.numberOfStocks.toString().toInt() * item.stockChange
            val totalPrice = roundOffDecimal(
                item.numberOfStocks.toString().toInt() *
                        item.averagePrice.toString().toDouble()
            )
            stockSymbol.text = item.stockSymbol
            stockAmount.text = item.numberOfStocks.toString()
            stockAveragePrice.text = item.averagePrice.toString()
            stockTotalPrice.text = totalPrice.toString()
            stockChange.text = change.toString()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.portfolio_stock_item, parent, false)
                return ViewHolder(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSetFiltered[position]
        viewHolder.bind(item)
    }

    override fun getItemCount() = dataSetFiltered.size

}