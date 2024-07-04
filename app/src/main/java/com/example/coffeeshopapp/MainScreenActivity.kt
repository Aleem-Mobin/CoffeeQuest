package com.example.coffeeshopapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.registerButton).setOnClickListener {
            startActivity(Intent(this, SignUpAndSignInActivity::class.java))
        }

        findViewById<Button>(R.id.browseButton).setOnClickListener {
            startActivity(Intent(this, CoffeeBrandsListActivity::class.java))
        }
    }
}