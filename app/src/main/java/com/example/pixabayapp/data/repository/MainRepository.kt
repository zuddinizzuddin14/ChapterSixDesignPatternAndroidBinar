package com.example.pixabayapp.data.repository

import com.example.pixabayapp.Resource
import com.example.pixabayapp.data.model.SearchResponse
import com.example.pixabayapp.data.service.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun searchPhoto(query: String): Resource<SearchResponse> = proceed {
        apiHelper.searchPhoto(query)
    }

    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }
}

