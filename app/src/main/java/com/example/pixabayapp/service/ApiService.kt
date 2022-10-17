package com.example.pixabayapp.service

import com.example.pixabayapp.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api")
    suspend fun searchPhoto(
        @Query("q", encoded = true) query: String,
        @Query("image_type") imageType: String = "photo",
    ): SearchResponse
}