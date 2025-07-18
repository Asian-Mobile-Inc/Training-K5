package issues13.ex2.repository

import issues13.ex2.model.ImageDao
import issues13.ex2.model.ImageModel
import issues13.ex2.retrofit.ImageApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageRepository(
    private val imageDao: ImageDao,
    private val imageApi: ImageApi
) {
    suspend fun getAllImages(): List<ImageModel> =
        withContext(Dispatchers.IO) {
            imageDao.selectAllImages()
        }

    suspend fun getFavoriteImages(favorite: Boolean): List<ImageModel> =
        withContext(Dispatchers.IO) {
            imageDao.selectFavoriteImages(favorite)
        }

    suspend fun fetchImagesFromServerApi() =
        withContext(Dispatchers.IO) {
            val result = imageApi.getImages()
            imageDao.deleteAll()
            imageDao.insertAllSafely(result)
        }

    suspend fun updateFavoriteImage(imageId: String, isFavorite: Boolean) =
        withContext(Dispatchers.IO) {
            imageDao.updateFavoriteImage(imageId, isFavorite)
        }
}
