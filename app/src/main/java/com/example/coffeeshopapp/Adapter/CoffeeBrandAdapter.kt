package com.example.coffeeshopapp.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshopapp.Model.CoffeeBrand
import com.example.coffeeshopapp.R

class CoffeeBrandAdapter(
    private var coffeeBrands: List<CoffeeBrand>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CoffeeBrandAdapter.CoffeeBrandViewHolder>() {

    // Interface for item click events
    interface OnItemClickListener {
        fun onItemClick(coffeeBrand: CoffeeBrand)
    }

    inner class CoffeeBrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val brandImageView: ImageView = itemView.findViewById(R.id.brandImageView)
        private val brandNameTextView: TextView = itemView.findViewById(R.id.brandNameTextView)

        fun bind(coffeeBrand: CoffeeBrand) {
            brandNameTextView.text = coffeeBrand.name
            brandImageView.setImageURI(Uri.parse(coffeeBrand.imageUri))
            itemView.setOnClickListener { listener.onItemClick(coffeeBrand) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeBrandViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coffee_brand, parent, false)
        return CoffeeBrandViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CoffeeBrandViewHolder, position: Int) {
        holder.bind(coffeeBrands[position])
    }

    override fun getItemCount(): Int {
        return coffeeBrands.size
    }

    // Method to add a new coffee brand to the list
    fun addItem(coffeeBrand: CoffeeBrand) {
        coffeeBrands = coffeeBrands + listOf(coffeeBrand)
        notifyItemInserted(coffeeBrands.size - 1)
    }
}
