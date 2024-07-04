package com.example.coffeeshopapp.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Items",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Item(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "itemId") var itemId: Long = 0,
    @ColumnInfo(name = "Name") var name: String,
    @ColumnInfo(name = "price") var price: String,
    @ColumnInfo(name = "contact") var contact: String,
    @ColumnInfo(name = "userId") var userId: Int // Foreign key to User table
)

