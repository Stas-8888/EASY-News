package com.example.newsapppp.domain.interactors.articles.remote

import androidx.paging.PagingData
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.model.NewsResponseModel
import kotlinx.coroutines.flow.Flow

interface ArticleRemoteContract {

    fun fetchedArticles(category: String): Flow<PagingData<ArticleModel>>

    suspend fun searchArticles(searchQuery: String): NewsResponseModel
}
