package com.example.coffeeshopapp

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshopapp.Adapter.ItemAdapter
import com.example.coffeeshopapp.Database.AppDatabase
import com.example.coffeeshopapp.Database.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddItemsListActivity : AppCompatActivity(), ItemAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private lateinit var addButton: Button
    private lateinit var emptyTextView: TextView

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_items_list)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        addButton = findViewById(R.id.addButton)
        emptyTextView = findViewById(R.id.emptyTextView)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        // Initialize adapter with an empty list initially
        adapter = ItemAdapter(emptyList(), this)
        recyclerView.adapter = adapter

        // Fetch items from database and update adapter
        fetchItemsAndUpdateUI()

        // Set up addButton click listener
        addButton.setOnClickListener {
            startActivity(Intent(this@AddItemsListActivity, AddItem::class.java))
        }
    }

    private fun fetchItemsAndUpdateUI() {
        val userId = sharedPreferences.getInt("userId", -1)

        if (userId == -1) {
            Log.e("AddItemsListActivity", "User ID not found in SharedPreferences")
            // Handle case where userId is not found
            return
        }

        val itemDao = AppDatabase.getDatabase(this).itemDao()

        lifecycleScope.launch(Dispatchers.IO) {
            val itemList = itemDao.getItemsForUser(userId)
            Log.d("AddItemsListActivity", "Fetched items: $itemList")
            launch(Dispatchers.Main) {
                if (itemList.isEmpty()) {
                    recyclerView.visibility = View.GONE
                    emptyTextView.visibility = View.VISIBLE
                } else {
                    recyclerView.visibility = View.VISIBLE
                    emptyTextView.visibility = View.GONE
                    adapter.setItemList(itemList)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchItemsAndUpdateUI()
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, CheckoutActivity::class.java)
        startActivity(intent)
    }

    companion object {
        const val ADD_ITEM_REQUEST = 1
    }

}
