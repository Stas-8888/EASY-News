package com.example.newsapppp.domain.repository

import com.example.newsapppp.domain.model.NewsResponseModel

interface ArticleRepository {

    suspend fun getNews(countryCode: String, category: String): NewsResponseModel

    suspend fun searchNews(searchQuery: String, pageNumber: Int): NewsResponseModel
}
