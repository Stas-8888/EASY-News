package com.example.newsapppp.domain.repository

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.model.NewsResponseModel
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    suspend fun insert(article: ArticleModel)

    suspend fun delete(article: ArticleModel)

    suspend fun deleteAll()

    fun getAllArticles(): Flow<List<ArticleModel>>

    suspend fun getNews(countryCode: String, category: String): NewsResponseModel

    suspend fun searchNews(searchQuery: String, pageNumber: Int): NewsResponseModel
}

