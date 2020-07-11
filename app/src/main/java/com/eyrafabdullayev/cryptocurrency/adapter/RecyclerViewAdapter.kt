package com.eyrafabdullayev.cryptocurrency.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eyrafabdullayev.cryptocurrency.R
import com.eyrafabdullayev.cryptocurrency.model.CryptoModel
import kotlinx.android.synthetic.main.row_layout.view.*

class RecyclerViewAdapter(private val cryptoList: ArrayList<CryptoModel>, val listener: Listener) :
    RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    interface Listener {
        fun onItemClick(cryptoModel: CryptoModel)
    }

    private val colors: Array<String> = arrayOf(
        "#13bd27",
        "#29c1e1",
        "#b129e1",
        "#d3df13",
        "#f6bd0c",
        "#a1fb93",
        "#0d9de3",
        "#ffe48f"
    )

    class RowHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(cryptoModel: CryptoModel, colors: Array<String>, position: Int, listener: Listener) {
            itemView.setOnClickListener { it ->
                listener.onItemClick(cryptoModel)
            }
            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))
            itemView.currencyText.text = cryptoModel.currency
            itemView.priceText.text = cryptoModel.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return RowHolder(view)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(cryptoList[position],colors,position,listener)
    }
}