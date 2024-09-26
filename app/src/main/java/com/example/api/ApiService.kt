package com.example.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): ApiResponse<Character>

    @GET("location")
    suspend fun getLocations(): ApiResponse<Location>

    @GET("episode")
    suspend fun getEpisodes(): ApiResponse<Episode>

    @GET
    fun getEpisodeByUrl(@Url url: String): Call<Episode>
}
