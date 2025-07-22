package issues13.ex2.repository

import issues13.ex2.model.ImageDao
import issues13.ex2.model.ImageModel
import issues13.ex2.retrofit.ImageApi

class ImageRepository(
    private val imageDao: ImageDao,
    private val imageApi: ImageApi
) {
    suspend fun getAllImages(): MutableList<ImageModel> = imageDao.selectAllImages()

    suspend fun getFavoriteImages(favorite: Boolean): MutableList<ImageModel> =
        imageDao.selectFavoriteImages(favorite)

    suspend fun fetchImagesFromServerApi() {
        val result = imageApi.getImages()
        imageDao.deleteAll()
        imageDao.insertAllSafely(result)
    }

    suspend fun updateFavoriteImage(imageId: String, isFavorite: Boolean) =
        imageDao.updateFavoriteImage(imageId, isFavorite)
}
