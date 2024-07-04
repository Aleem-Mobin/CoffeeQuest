package com.example.coffeeshopapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.util.Log

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Log.d("SplashActivity", "onCreate called")

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainScreenActivity::class.java))
            finish()
        }, 3000) // 3 seconds delay
    }
}