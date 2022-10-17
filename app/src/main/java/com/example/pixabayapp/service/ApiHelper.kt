package com.example.pixabayapp.service

class ApiHelper(private val apiService: ApiService) {
    
    suspend fun searchPhoto(query: String) = apiService.searchPhoto(query)
}