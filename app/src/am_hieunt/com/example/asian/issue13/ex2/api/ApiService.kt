package com.example.asian.issue13.ex2.api

import com.example.asian.issue13.ex2.model.Image
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    companion object {
        var apiService: ApiService = Retrofit.Builder()
            .baseUrl("https://api.gyazo.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @GET("images")
    suspend fun getImages(@Header("Authorization") token: String) : Response<List<Image>>
}
