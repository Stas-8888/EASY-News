package com.example.newsapppp.domain.interactors.articleremote

import androidx.paging.PagingData
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.model.NewsResponseModel
import kotlinx.coroutines.flow.Flow

interface ArticleRemoteContract {

    suspend fun getNews(category: String): Flow<PagingData<ArticleModel>>

    suspend fun searchNews(searchQuery: String): NewsResponseModel
}