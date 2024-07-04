package com.example.coffeeshopapp.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.coffeeshopapp.Database.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM user_table WHERE contactNumber = :contactNumber AND password = :password")
    suspend fun getUser(contactNumber: String, password: String): User?

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): List<User>
}