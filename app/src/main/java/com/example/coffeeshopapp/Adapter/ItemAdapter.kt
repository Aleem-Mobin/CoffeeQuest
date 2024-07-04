package com.example.coffeeshopapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshopapp.Database.Item
import com.example.coffeeshopapp.R

class ItemAdapter(
    private var itemList: List<Item>,
    private val clickListener: OnItemClickListener
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // Interface for item click events
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.name)
        val itemPrice: TextView = view.findViewById(R.id.price)
        val itemContact: TextView = view.findViewById(R.id.contact)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.itemName.text = item.name
        holder.itemPrice.text = item.price
        holder.itemContact.text = item.contact

        holder.itemView.setOnClickListener {
            clickListener.onItemClick(position)
        }
    }

    override fun getItemCount() = itemList.size

    fun setItemList(items: List<Item>) {
        itemList = items
        notifyDataSetChanged()
    }
}
