package com.example.pixabayapp.service

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val API_KEY = "26395334-cf73edfe10b3abc71ab380061"
    private const val BASE_URL = "https://pixabay.com/"

    private val authInterceptor = Interceptor {
        val originRequest = it.request()
        val newUrl = originRequest.url.newBuilder().apply {
            addQueryParameter("key", API_KEY)
        }.build()
        it.proceed(originRequest.newBuilder().url(newUrl).build())
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        retrofit.create(ApiService::class.java)
    }
}