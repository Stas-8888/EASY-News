package com.example.newsapppp.domain.interactors.articlecache

import com.example.newsapppp.domain.model.ArticleModel
import kotlinx.coroutines.flow.Flow

interface ArticleCacheContract {

    suspend fun insertArticle(article: ArticleModel)

    suspend fun deleteArticle(article: ArticleModel)

    suspend fun deleteAllArticle()

    fun getAllArticles(): Flow<List<ArticleModel>>
}
