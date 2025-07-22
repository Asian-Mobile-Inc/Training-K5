package com.example.asian.issue13.ex2.repository

import androidx.lifecycle.LiveData
import com.example.asian.issue13.ex2.database.ImageDAO
import com.example.asian.issue13.ex2.model.Image

class ImageRepository(private var imageDAO: ImageDAO) {
    val allImage: LiveData<List<Image>> = imageDAO.getAllImage()
    suspend fun insertImage(image: Image) {
        imageDAO.insertImage(image)
    }
    suspend fun updateImage(image: Image) {
        imageDAO.updateImage(image)
    }
}