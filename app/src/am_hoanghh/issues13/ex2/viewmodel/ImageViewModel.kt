package issues13.ex2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import issues13.ex2.model.ImageModel
import issues13.ex2.repository.ImageRepository
import kotlinx.coroutines.launch

class ImageViewModel(private val repository: ImageRepository) : ViewModel() {
    private val _images = MutableLiveData<MutableList<ImageModel>>()
    val images: LiveData<MutableList<ImageModel>> get() = _images

    suspend fun selectAllImages() {
        val data = repository.getAllImages()
        _images.postValue(data)
    }

    suspend fun selectFavoriteImages(favorite: Boolean) {
        val data = repository.getFavoriteImages(favorite)
        _images.postValue(data)
    }

    fun addImagesFromServerApi() = viewModelScope.launch {
        try {
            repository.fetchImagesFromServerApi()
            selectAllImages()
        } catch (e: Exception) {
            Log.e(DEBUG, ERROR_LOADING_IMAGES, e)
        }
    }

    fun updateFavoriteImage(imageId: String, isFavorite: Boolean) = viewModelScope.launch {
        repository.updateFavoriteImage(imageId, isFavorite)
        selectAllImages()
    }

    fun updateNotFavoriteImage(imageId: String, isFavorite: Boolean) = viewModelScope.launch {
        repository.updateFavoriteImage(imageId, isFavorite)
        selectFavoriteImages(true)
    }

    companion object {
        private const val DEBUG = "DEBUG"
        private const val ERROR_LOADING_IMAGES = "Error loading images"
    }
}
