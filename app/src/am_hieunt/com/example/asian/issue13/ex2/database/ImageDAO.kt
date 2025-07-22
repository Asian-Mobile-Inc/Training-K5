package com.example.asian.issue13.ex2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.asian.issue13.ex2.model.Image

@Dao interface ImageDAO {
    @Query("SELECT * FROM image")
    fun getAllImage(): LiveData<List<Image>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: Image)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateImage(image: Image)
}