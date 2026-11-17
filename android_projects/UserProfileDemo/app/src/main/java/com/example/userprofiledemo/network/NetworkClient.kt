package com.example.userprofiledemo.network

import com.example.userprofiledemo.data.models.UserProfile
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

// 1. API Interface (HTTP Methods)
interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<UserProfile>

    @GET("users/{id}")
    suspend fun getUserProfile(id: Int): UserProfile
}

// 2. Retrofit Architecture
object NetworkClient {
    private const val BASE_URL = "https://dummyjson.com/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val converter = json.asConverterFactory(
        "application/json".toMediaType()
    )

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(converter)
        .build()

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}