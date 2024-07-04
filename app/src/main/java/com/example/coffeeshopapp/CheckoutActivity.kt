package com.example.coffeeshopapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CheckoutActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var countryEditText: EditText
    private lateinit var cityEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var codCheckBox: CheckBox
    private lateinit var visaCheckBox: CheckBox
    private lateinit var tickButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        emailEditText = findViewById(R.id.email)
        nameEditText = findViewById(R.id.name)
        phoneEditText = findViewById(R.id.phone)
        countryEditText = findViewById(R.id.country)
        cityEditText = findViewById(R.id.city)
        addressEditText = findViewById(R.id.address)
        codCheckBox = findViewById(R.id.cod)
        visaCheckBox = findViewById(R.id.visa)
        tickButton = findViewById(R.id.tick_button)

        tickButton.setOnClickListener {
            if (validateFields()) {
                // Redirect to 'Add Items' list
                val intent = Intent(this, CoffeeBrandsListActivity::class.java)
                startActivity(intent)
                // Show toast message
                Toast.makeText(this, "Your order has been placed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateFields(): Boolean {
        var isValid = true

        if (emailEditText.text.isEmpty()) {
            emailEditText.error = "You can't leave this field empty"
            isValid = false
        }
        if (nameEditText.text.isEmpty()) {
            nameEditText.error = "You can't leave this field empty"
            isValid = false
        }
        if (phoneEditText.text.isEmpty()) {
            phoneEditText.error = "You can't leave this field empty"
            isValid = false
        }
        if (countryEditText.text.isEmpty()) {
            countryEditText.error = "You can't leave this field empty"
            isValid = false
        }
        if (cityEditText.text.isEmpty()) {
            cityEditText.error = "You can't leave this field empty"
            isValid = false
        }
        if (addressEditText.text.isEmpty()) {
            addressEditText.error = "You can't leave this field empty"
            isValid = false
        }

        return isValid
    }
}