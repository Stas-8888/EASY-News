package com.example.newsapppp.data.articles.remote.service

import com.example.newsapppp.data.articles.remote.model.NewsResponseRemote
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/top-headlines")
    suspend fun fetchedArticles(
        @Query("page") page: Int = 0,
        @Query("country") countryCode: String = "us",
        @Query("category") category: String = ""
    ): NewsResponseRemote

    @GET("v2/everything")
    suspend fun searchArticles(
        @Query("q") searchQuery: String
    ): NewsResponseRemote
}
