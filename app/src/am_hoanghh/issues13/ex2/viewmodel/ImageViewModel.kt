package issues13.ex2.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import issues13.ex2.database.DatabaseProvider
import issues13.ex2.model.ImageModel
import issues13.ex2.retrofit.ImageApi
import issues13.ex2.retrofit.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class ImageViewModel(context: Context) : ViewModel() {
    private val db = DatabaseProvider.getDatabase(context)
    private val imageDao = db.imageDao()
    private val _images =  MutableLiveData<List<ImageModel>>()
    val images: LiveData<List<ImageModel>> get() = _images
    private val retrofit: Retrofit = RetrofitClient.getClient()
    private val imageApi = retrofit.create(ImageApi::class.java)

    fun selectAllImages() {
        viewModelScope.launch {
            val data = imageDao.selectAllImages()
            _images.postValue(data)
        }
    }

    fun selectFavoriteImages(favorite: Boolean) {
        viewModelScope.launch {
            val data = imageDao.selectFavoriteImages(favorite)
            _images.postValue(data)
        }
    }

    fun addImagesFromServerApi() {
        viewModelScope.launch {
            try {
                val result = imageApi.getImages()
                imageDao.deleteAll()
                imageDao.insertAllSafely(result)
                selectAllImages()
            } catch (e: Exception) {
                Log.e("DEBUG", "Error loading images", e)
            }
        }
    }

    fun updateFavoriteImage(imageId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            imageDao.updateFavoriteImage(imageId, isFavorite)
            selectAllImages()
        }
    }

    fun updateNotFavoriteImage(imageId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            imageDao.updateFavoriteImage(imageId, isFavorite)
            selectFavoriteImages(true)
        }
    }
}
