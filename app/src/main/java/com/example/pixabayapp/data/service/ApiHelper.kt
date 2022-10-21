package com.example.pixabayapp.data.service

class ApiHelper(private val apiService: ApiService) {
    suspend fun searchPhoto(query: String) = apiService.searchPhoto(query)
}
