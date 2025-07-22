package com.example.asian.issue13.ex2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.asian.issue13.ex2.database.ImageDatabase
import com.example.asian.issue13.ex2.model.Image
import com.example.asian.issue13.ex2.repository.ImageRepository
import kotlinx.coroutines.launch

class ImageViewModel(application: Application): AndroidViewModel(application) {
    private val allImage: LiveData<List<Image>>
    private val repository: ImageRepository
    init {
        val imageDAO = ImageDatabase.getDatabase(application).imageDAO()
        repository = ImageRepository(imageDAO)
        allImage = repository.allImage
    }
    fun updateImage(image: Image) = viewModelScope.launch {
        repository.updateImage(image)
    }
    fun addImage(image: Image) = viewModelScope.launch {
        repository.insertImage(image)
    }
}