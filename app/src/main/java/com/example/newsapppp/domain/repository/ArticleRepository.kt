package com.example.newsapppp.domain.repository

import androidx.paging.PagingData
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.model.NewsResponseModel
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    fun fetchedArticles(category: String): Flow<PagingData<ArticleModel>>

    suspend fun searchArticles(searchQuery: String): NewsResponseModel
}
