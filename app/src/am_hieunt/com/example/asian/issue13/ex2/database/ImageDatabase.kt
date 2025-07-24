package com.example.asian.issue13.ex2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.asian.issue13.ex1.database.UserDatabase
import com.example.asian.issue13.ex1.database.UserDatabase.Companion
import com.example.asian.issue13.ex2.model.Image

@Database(entities = [Image::class], version = 1, exportSchema = false)
abstract class ImageDatabase: RoomDatabase() {
    abstract fun imageDAO(): ImageDAO
    companion object {
        private var instance: ImageDatabase ?= null
        fun getDatabase(context: Context): ImageDatabase {
            return instance ?: synchronized(this) {
                val ins = Room.databaseBuilder(
                    context.applicationContext,
                    ImageDatabase::class.java,
                    "image_database"
                ).build()
                ImageDatabase.instance = ins
                ins
            }
        }
    }
}
