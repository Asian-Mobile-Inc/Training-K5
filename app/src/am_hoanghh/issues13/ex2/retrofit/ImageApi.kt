package issues13.ex2.retrofit

import issues13.ex2.model.ImageModel
import retrofit2.http.GET

interface ImageApi {
    @GET("images")
    suspend fun getImages(): MutableList<ImageModel>
}
