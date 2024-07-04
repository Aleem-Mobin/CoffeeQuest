package com.example.coffeeshopapp

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.coffeeshopapp.Database.AppDatabase
import com.example.coffeeshopapp.Database.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddItem : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var contactEditText: EditText
    private lateinit var tickButton: Button

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        nameEditText = findViewById(R.id.name)
        priceEditText = findViewById(R.id.price)
        contactEditText = findViewById(R.id.contact)
        tickButton = findViewById(R.id.tick_button)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        tickButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val price = priceEditText.text.toString().trim()
            val contact = contactEditText.text.toString().trim()

            if (name.isEmpty() || price.isEmpty() || contact.isEmpty()) {
                Toast.makeText(this@AddItem, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userId = sharedPreferences.getInt("userId", -1)

            if (userId == -1) {
                Log.e("AddItemActivity", "User ID not found in SharedPreferences")
                return@setOnClickListener
            }

            val item = Item(name = name, price = price, contact = contact, userId = userId)

            // Save item to database
            saveItemAndUpdateUI(item)
        }
    }

    private fun saveItemAndUpdateUI(item: Item) {
        val itemDao = AppDatabase.getDatabase(this).itemDao()

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                itemDao.insert(item)

                launch(Dispatchers.Main) {
                    Toast.makeText(this@AddItem, "Item added successfully", Toast.LENGTH_SHORT).show()

                    // Set result and finish activity to return to AddItemsListActivity
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            } catch (e: Exception) {
                Log.e("AddItemActivity", "Error saving item", e)
                launch(Dispatchers.Main) {
                    Toast.makeText(this@AddItem, "Failed to add item", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
