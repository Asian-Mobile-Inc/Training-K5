package com.example.asian.issue13.ex2.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "image")
data class Image(@PrimaryKey @ColumnInfo("url") var url: String) {
    @ColumnInfo("image") var img: ByteArray ?= null
    @ColumnInfo("favorite") var favorite: Boolean = false
}
