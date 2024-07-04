package com.example.coffeeshopapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SignUpAndSignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_and_sign_in)

        // Initialize buttons
        val signUpButton: Button = findViewById(R.id.signUpButton)
        val signInButton: Button = findViewById(R.id.signInButton)

        // Set click listeners
        signUpButton.setOnClickListener {
            // Handle Sign Up button click
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        signInButton.setOnClickListener {
            // Handle Sign In button click
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}