package com.example.api

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("photos")
    suspend fun getPhotos(): Response<List<Photo>>
}

