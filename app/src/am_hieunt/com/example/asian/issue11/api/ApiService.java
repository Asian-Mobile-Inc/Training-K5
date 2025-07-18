package com.example.asian.issue11.api;

import com.example.asian.issue11.model.Image;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://api.gyazo.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

    @GET("images")
    Call<List<Image>> getImages(@Header("Authorization") String token);

    @Multipart
    @POST("https://upload.gyazo.com/api/upload")
    Call<Image> uploadImage(@Header("Authorization") String token, @Part MultipartBody.Part imageData);

    @DELETE("images/{id}")
    Call<ResponseBody> deleteImage(@Header("Authorization") String token, @Path("id") String id);
}
