package com.example.newsapppp.domain.repository

import androidx.paging.PagingData
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.model.NewsResponseModel
import kotlinx.coroutines.flow.Flow

interface ArticleRepositoryContract {

    suspend fun getNews(category: String): Flow<PagingData<ArticleModel>>

    suspend fun searchNews(searchQuery: String): NewsResponseModel
}
