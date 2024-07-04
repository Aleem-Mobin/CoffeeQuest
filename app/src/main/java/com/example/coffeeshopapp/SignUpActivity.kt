package com.example.coffeeshopapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.coffeeshopapp.Database.AppDatabase
import com.example.coffeeshopapp.Database.User
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var brandNameEditText: EditText
    private lateinit var contactNumberEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpButton: Button

    private var selectedImageUri: Uri? = null

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            imageView.setImageURI(it)
            selectedImageUri = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        imageView = findViewById(R.id.imageView)
        brandNameEditText = findViewById(R.id.brandNameEditText)
        contactNumberEditText = findViewById(R.id.contactNumberEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        signUpButton = findViewById(R.id.signUpButton)

        imageView.setOnClickListener {
            selectImageLauncher.launch("image/*")
        }

        signUpButton.setOnClickListener {
            val brandName = brandNameEditText.text.toString()
            val contactNumber = contactNumberEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (brandName.isNotBlank() && contactNumber.isNotBlank() && password.isNotBlank() && selectedImageUri != null) {
                val user = User(
                    brandName = brandName,
                    contactNumber = contactNumber,
                    password = password,
                    imageUri = selectedImageUri.toString()
                )
                saveUser(user)
            } else {
                Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUser(user: User) {
        lifecycleScope.launch {
            AppDatabase.getDatabase(applicationContext).userDao().insert(user)
            startActivity(Intent(this@SignUpActivity, SignUpAndSignInActivity::class.java))
            finish()
            Toast.makeText(this@SignUpActivity, "Brand registered successfully", Toast.LENGTH_SHORT).show()
        }
    }
}