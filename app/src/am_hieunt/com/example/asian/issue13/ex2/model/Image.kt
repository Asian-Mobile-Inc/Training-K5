package com.example.asian.issue13.ex2.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image")
data class Image(@ColumnInfo("url") var url:String, @ColumnInfo("favorite") var favorite: Boolean) {
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") var id: Int = 0
//    @ColumnInfo("img") var img: Bitmap ?= null
}
