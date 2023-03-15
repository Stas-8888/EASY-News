package com.example.newsapppp.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapppp.core.dispatcher.DispatcherRepositoryContract
import com.example.newsapppp.data.cache.SharedPreferences
import com.example.newsapppp.data.mapper.NewsResponseMapper
import com.example.newsapppp.data.remote.paging.ArticlePagingSource
import com.example.newsapppp.data.remote.service.ApiService
import com.example.newsapppp.domain.interactors.articleremote.ArticleRemoteContract
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.model.NewsResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleRemote @Inject constructor(
    private val apiService: ApiService,
    private val mapper: NewsResponseMapper,
    private val sharedPref: SharedPreferences,
    private val dispatcher: DispatcherRepositoryContract,
) : ArticleRemoteContract {

    override suspend fun fetchedArticles(category: String): Flow<PagingData<ArticleModel>> {
        return dispatcher.io {
            Pager(
                config = PagingConfig(pageSize = 20, maxSize = 200, enablePlaceholders = false),
                pagingSourceFactory = {
                    ArticlePagingSource(apiService, sharedPref.getCountryFlag(), category, mapper)
                }
            ).flow
        }
    }

    override suspend fun searchNews(searchQuery: String): NewsResponseModel = dispatcher.io {
        val data = apiService.searchArticles(searchQuery)
        mapper.converterToNewsResponseModel(data)
    }
}
