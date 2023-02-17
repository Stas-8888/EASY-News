package com.example.newsapppp.data.network.service

import com.example.newsapppp.BuildConfig
import com.example.newsapppp.data.network.model.NewsResponseRemote
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = BuildConfig.API_KEY

interface ApiService {
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("page")
        page: Int = 0,
        @Query("country")
        countryCode: String = "us",
        @Query("category")
        category: String = "",
        @Query("apiKey")
        apiKey: String = API_KEY,
    ): NewsResponseRemote

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsResponseRemote
}
