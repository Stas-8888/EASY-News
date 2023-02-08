package com.example.newsapppp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapppp.core.DispatcherRepositoryContract
import com.example.newsapppp.data.mapper.NewsResponseMapper
import com.example.newsapppp.data.network.service.ApiService
import com.example.newsapppp.data.paging.ArticlePagingSource
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.model.NewsResponseModel
import com.example.newsapppp.domain.repository.ArticleRepositoryContract
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val apiService: ApiService,
    private val mapper: NewsResponseMapper,
    private val sharedPref: SharedPrefRepository,
    private val dispatcher: DispatcherRepositoryContract,
) : ArticleRepositoryContract {

    override suspend fun getNews(category: String): Flow<PagingData<ArticleModel>> = dispatcher.io {
        return@io Pager(
            config = PagingConfig(pageSize = 20, maxSize = 200, enablePlaceholders = false),
            pagingSourceFactory = {
                ArticlePagingSource(apiService, sharedPref.getCountryFlag(), category, mapper)
            }
        ).flow
    }

    override suspend fun searchNews(searchQuery: String): NewsResponseModel = dispatcher.io {
        val data = apiService.searchForNews(searchQuery)
        mapper.converterToNewsResponseModel(data)
    }
}
