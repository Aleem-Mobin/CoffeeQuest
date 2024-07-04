package com.example.coffeeshopapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshopapp.Adapter.CoffeeBrandAdapter
import com.example.coffeeshopapp.Database.AppDatabase
import com.example.coffeeshopapp.Model.CoffeeBrand
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoffeeBrandsListActivity : AppCompatActivity(), CoffeeBrandAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CoffeeBrandAdapter
    private lateinit var noBrandsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coffee_brands_list)

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        noBrandsTextView = findViewById(R.id.noBrandsTextView)

        // Fetch coffee brands from the database and set the adapter
        fetchCoffeeBrands()
    }

    private fun fetchCoffeeBrands() {
        lifecycleScope.launch {
            val userDao = AppDatabase.getDatabase(applicationContext).userDao()
            val users = userDao.getAllUsers()
            val coffeeBrands = users.map { user ->
                CoffeeBrand(user.brandName, user.imageUri, user.id)
            }

            // Update UI based on coffee brands list
            if (coffeeBrands.isEmpty()) {
                recyclerView.visibility = View.GONE
                noBrandsTextView.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                noBrandsTextView.visibility = View.GONE

                adapter = CoffeeBrandAdapter(coffeeBrands, this@CoffeeBrandsListActivity)
                recyclerView.adapter = adapter
            }
        }
    }

    // Handle item click to navigate to the next activity
    override fun onItemClick(coffeeBrand: CoffeeBrand) {
        val intent = Intent(this, BrowswListActivity::class.java).apply {
            putExtra("BRAND_NAME", coffeeBrand.name)
            putExtra("IMAGE_URI", coffeeBrand.imageUri)
            putExtra("USER_ID", coffeeBrand.id)
        }
        startActivity(intent)
    }
}
