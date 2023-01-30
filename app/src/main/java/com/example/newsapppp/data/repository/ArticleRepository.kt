package com.example.newsapppp.data.repository

import com.example.newsapppp.core.DispatcherRepositoryContract
import com.example.newsapppp.data.mapper.NewsResponseMapper
import com.example.newsapppp.data.network.service.ApiService
import com.example.newsapppp.domain.model.NewsResponseModel
import com.example.newsapppp.domain.repository.ArticleRepositoryContract
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val apiService: ApiService,
    private val mapper: NewsResponseMapper,
    private val sharedPref: SharedPrefRepository,
    private val dispatcher: DispatcherRepositoryContract,
) : ArticleRepositoryContract {

    override suspend fun getNews(category: String): NewsResponseModel = dispatcher.io {
        val country = sharedPref.getCountryFlag()
        val data = apiService.getBreakingNews(countryCode = country, category = category)
        mapper.converterToNewsResponseModel(data)
    }

    override suspend fun searchNews(searchQuery: String): NewsResponseModel = dispatcher.io {
        val data = apiService.searchForNews(searchQuery)
        mapper.converterToNewsResponseModel(data)
    }
}
