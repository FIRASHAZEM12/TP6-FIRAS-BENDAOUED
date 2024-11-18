package com.example.marsphotos

import MarsPhoto
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

// URL de base pour le service web
private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

// Interface MarsApiService
interface MarsApiService {
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhoto>
}

// Singleton pour gérer Retrofit
object MarsApi {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}
