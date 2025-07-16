package issues13.ex2.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.gyazo.com/api/"
    private const val AUTHORIZATION = "Authorization"
    private const val ACCESS_TOKEN = "Sid3U1uAZenKuA9KHcQQznFzXUe87IhnQXD6Hfhs3_M"

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val modifiedRequest = originalRequest.newBuilder()
                .addHeader(AUTHORIZATION, "Bearer $ACCESS_TOKEN")
                .build()
            chain.proceed(modifiedRequest)
        }
        .build()

    fun getClient(): Retrofit =
        Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}
