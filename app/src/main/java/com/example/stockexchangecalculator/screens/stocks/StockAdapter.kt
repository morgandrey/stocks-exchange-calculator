package com.example.stockexchangecalculator.screens.stocks

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.stockexchangecalculator.R
import com.example.stockexchangecalculator.data.models.Stock
import java.security.AccessController.getContext
import java.util.*

class StockAdapter(private var dataSet: MutableList<Stock>) :
    RecyclerView.Adapter<StockAdapter.ViewHolder>(), Filterable {

    var dataSetFiltered = mutableListOf<Stock>()

    init {
        dataSetFiltered = dataSet
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val stockSymbol: TextView = itemView.findViewById(R.id.stock_symbol)
        private val stockPrice: TextView = itemView.findViewById(R.id.stock_price)
        private val stockChange: TextView = itemView.findViewById(R.id.stock_change)
        private val changeImage: ImageView = itemView.findViewById(R.id.change_image)
        private val stockName: TextView = itemView.findViewById(R.id.stock_name)

        fun bind(item: Stock) {
            stockSymbol.text = item.stockSymbol
            stockPrice.text = item.stockPrice.toString()
            stockChange.text = item.stockChange.toString()
            stockName.text = item.stockName
            changeImageAndTextView(stockChange, changeImage, item.stockChange)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.recycle_view_item, parent, false)
                return ViewHolder(view)
            }
        }

        private fun changeImageAndTextView(
            textView: TextView,
            changeImage: ImageView,
            change: Double
        ) {
            when {
                change < 0.0 -> {
                    textView.setTextColor(Color.RED)
                    changeImage.setImageResource(R.drawable.red_arrow)
                }
                change > 0.0 -> {
                    textView.setTextColor(Color.parseColor("#29B52E"))
                    changeImage.setImageResource(R.drawable.green_arrow)
                }
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                dataSetFiltered = if (charSearch.isEmpty()) {
                    dataSet
                } else {
                    val resultList = mutableListOf<Stock>()
                    for (row in dataSet) {
                        if (row.stockSymbol
                                .toLowerCase(Locale.ROOT)
                                .startsWith(charSearch.toLowerCase(Locale.ROOT))
                            || row.stockName
                                .toLowerCase(Locale.ROOT)
                                .startsWith(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = dataSetFiltered
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataSetFiltered = results?.values as MutableList<Stock>
                notifyDataSetChanged()
            }
        }
    }

}
