package com.example.coffeeshopapp.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val brandName: String,
    val contactNumber: String,
    val password: String,
    val imageUri: String
)
