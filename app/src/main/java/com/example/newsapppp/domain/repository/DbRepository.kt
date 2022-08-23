package com.example.newsapppp.domain.repository

import com.example.newsapppp.domain.model.ArticleModel
import kotlinx.coroutines.flow.Flow

interface DbRepository {

    suspend fun insertArticle(article: ArticleModel)

    suspend fun deleteArticle(article: ArticleModel)

    suspend fun deleteAllArticle()

    fun getAllArticles(): Flow<List<ArticleModel>>

}
