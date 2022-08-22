package com.example.newsapppp.domain.repository

import com.example.newsapppp.domain.model.ArticleModel
import kotlinx.coroutines.flow.Flow

interface DbRepository {

    suspend fun insert(article: ArticleModel)

    suspend fun delete(article: ArticleModel)

    suspend fun deleteAll()

    fun getAllArticles(): Flow<List<ArticleModel>>

}
