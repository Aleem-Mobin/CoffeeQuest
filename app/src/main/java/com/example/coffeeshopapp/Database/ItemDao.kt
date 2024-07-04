package com.example.coffeeshopapp.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item): Long

    @Query("SELECT * FROM Items WHERE userId = :userId")
    suspend fun getItemsForUser(userId: Int): List<Item>

    @Delete
    suspend fun delete(item: Item)
}
