package com.example.coffeeshopapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.coffeeshopapp.Database.AppDatabase
import com.example.coffeeshopapp.Database.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInActivity : AppCompatActivity() {

    private lateinit var contactNumberEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        contactNumberEditText = findViewById(R.id.contactNumberEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        signInButton = findViewById(R.id.signInButton)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        signInButton.setOnClickListener {
            val contactNumber = contactNumberEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (contactNumber.isNotBlank() && password.isNotBlank()) {
                authenticateUser(contactNumber, password)
            } else {
                Toast.makeText(this, "Please enter your contact number and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun authenticateUser(contactNumber: String, password: String) {
        lifecycleScope.launch {
            val userDao = AppDatabase.getDatabase(applicationContext).userDao()
            val user = userDao.getUser(contactNumber, password)

            if (user != null) {
                // Store userId in SharedPreferences
                sharedPreferences.edit().putInt("userId", user.id).apply()

                val intent = Intent(this@SignInActivity, AddItemsListActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this@SignInActivity, "User has signed in", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@SignInActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
