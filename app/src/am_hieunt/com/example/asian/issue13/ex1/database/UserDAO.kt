package com.example.asian.issue13.ex1.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.asian.issue13.ex1.model.User

@Dao interface UserDAO {
    @Query("SELECT * FROM User")
    fun getAllUser(): LiveData<List<User>>
    @Query("SELECT * FROM User WHERE user_id = :id")
    suspend fun getUserById(id: Int): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)
    @Delete
    suspend fun deleteUser(user: User)
    @Query("DELETE FROM User")
    suspend fun deleteAllUser();
}