package com.example.newsapppp.data.repository

import com.example.newsapppp.core.DispatchersList
import com.example.newsapppp.data.mapper.NewsResponseMapper
import com.example.newsapppp.data.network.ApiService
import com.example.newsapppp.domain.model.NewsResponseModel
import com.example.newsapppp.domain.repository.ArticleRepository
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val newsResponseMapper: NewsResponseMapper,
    private val sharedPref: SharedPrefRepositoryImpl,
    private val dispatchers: DispatchersList
) : ArticleRepository {

    override suspend fun getNews(category: String): NewsResponseModel =
        dispatchers.withContextIO {
            try {
                val country = sharedPref.getCountryFlag()
                val data =
                    apiService.getBreakingNews(countryCode = country, category = category)
                newsResponseMapper.converterToNewsResponseModel(data)
            } catch (ex: Exception) {
                throw ex
            }
        }

    override suspend fun searchNews(searchQuery: String): NewsResponseModel =
        dispatchers.withContextIO {
            try {
                val data = apiService.searchForNews(searchQuery)
                newsResponseMapper.converterToNewsResponseModel(data)
            } catch (ex: Exception) {
                throw IllegalStateException(ex)
            }
        }
}
