package com.example.pixabayapp.repository

import com.example.pixabayapp.service.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun searchPhoto(query: String) = apiHelper.searchPhoto(query)
}