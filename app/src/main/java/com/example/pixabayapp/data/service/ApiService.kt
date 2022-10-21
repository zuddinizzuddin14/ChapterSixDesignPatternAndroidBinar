package com.example.pixabayapp.data.service

import com.example.pixabayapp.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api")
    suspend fun searchPhoto(
        @Query("q", encoded = true) query: String,
        @Query("image_type") imageType: String = "photo",
    ): SearchResponse
}