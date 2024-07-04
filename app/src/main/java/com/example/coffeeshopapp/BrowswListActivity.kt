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
import com.example.coffeeshopapp.Adapter.ItemAdapter
import com.example.coffeeshopapp.Database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BrowswListActivity : AppCompatActivity(), ItemAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private lateinit var emptyTextView: TextView

    private var userId: Int = -1 // Initialize to an invalid value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browsw_list)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        emptyTextView = findViewById(R.id.emptyTextView)

        // Initialize adapter with an empty list initially
        adapter = ItemAdapter(emptyList(), this)
        recyclerView.adapter = adapter

        // Fetch userId from intent extras
        userId = intent.getIntExtra("USER_ID", -1)

        // Fetch items for the retrieved userId
        fetchItemsAndUpdateUI()
    }

    private fun fetchItemsAndUpdateUI() {
        val itemDao = AppDatabase.getDatabase(this).itemDao()

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val itemList = itemDao.getItemsForUser(userId)
                Log.d("BrowswListActivity", "Fetched items: $itemList")
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
            } catch (e: Exception) {
                Log.e("BrowswListActivity", "Error fetching items", e)
            }
        }
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, CheckoutActivity::class.java)
        startActivity(intent)
    }
}
