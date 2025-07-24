package com.example.asian.issue13.ex2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.asian.issue13.ex2.database.ImageDatabase
import com.example.asian.issue13.ex2.model.Image
import com.example.asian.issue13.ex2.repository.ImageRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ImageViewModel(application: Application): AndroidViewModel(application) {
    val allImage: LiveData<List<Image>>
    private val repository: ImageRepository
    init {
        val imageDAO = ImageDatabase.getDatabase(application).imageDAO()
        repository = ImageRepository(imageDAO)
        allImage = repository.allImage
    }
    suspend fun addImage(image: Image) {
        repository.insertImage(image)
    }
    fun deleteImage(image: Image) = viewModelScope.launch {
        repository.deleteImage(image)
    }
}
