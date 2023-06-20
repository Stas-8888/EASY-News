package com.example.newsapppp.domain.repository

import com.example.newsapppp.domain.model.ArticleModel
import kotlinx.coroutines.flow.Flow

interface ArticleCacheRepository {

    suspend fun insertArticle(article: ArticleModel)

    suspend fun deleteArticle(article: ArticleModel)

    fun getAllArticles(): Flow<List<ArticleModel>>

    suspend fun deleteAllArticle()
}
