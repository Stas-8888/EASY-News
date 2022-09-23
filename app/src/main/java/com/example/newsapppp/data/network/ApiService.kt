package com.example.newsapppp.data.network

import com.example.newsapppp.data.network.model.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query
//https://newsapi.org/v2/top-headlines?country=us&apiKey=a0e436b60013442b85ed2ab11d93dbfb
private const val API_KEY = "a0e436b60013442b85ed2ab11d93dbfb"

interface ApiService {
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("category")
        category: String = "",
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsResponseDto

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsResponseDto
}
