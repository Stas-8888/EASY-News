package com.example.newsapppp.data.repository

import com.example.newsapppp.core.Dispatchers
import com.example.newsapppp.data.mapper.NewsResponseMapper
import com.example.newsapppp.data.network.ApiService
import com.example.newsapppp.domain.model.NewsResponseModel
import com.example.newsapppp.domain.repository.ArticleRepositoryContract
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val apiService: ApiService,
    private val newsResponseMapper: NewsResponseMapper,
    private val sharedPref: SharedPrefRepository,
    private val dispatchers: Dispatchers
) : ArticleRepositoryContract {

    override suspend fun getNews(category: String): NewsResponseModel =
        dispatchers.io {
            val country = sharedPref.getCountryFlag()
            val data = apiService.getBreakingNews(countryCode = country, category = category)
            newsResponseMapper.converterToNewsResponseModel(data)
        }

    override suspend fun searchNews(searchQuery: String): NewsResponseModel =
        dispatchers.io {
            val data = apiService.searchForNews(searchQuery)
            newsResponseMapper.converterToNewsResponseModel(data)
        }
}
