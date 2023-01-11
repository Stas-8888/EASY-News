package com.example.newsapppp.domain.repository

import com.example.newsapppp.domain.model.NewsResponseModel

interface ArticleRepository {

    suspend fun getNews(category: String): NewsResponseModel

    suspend fun searchNews(searchQuery: String): NewsResponseModel
}
