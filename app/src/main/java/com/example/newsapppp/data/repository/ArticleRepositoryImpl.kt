package com.example.newsapppp.data.repository

import com.example.newsapppp.core.DispatchersList
import com.example.newsapppp.data.mapper.NewsResponseMapper
import com.example.newsapppp.data.network.ApiService
import com.example.newsapppp.domain.model.NewsResponseModel
import com.example.newsapppp.domain.repository.ArticleRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val newsResponseMapper: NewsResponseMapper,
    private val dispatchers: DispatchersList.Base
) : ArticleRepository {

    override suspend fun getNews(countryCode: String, category: String): NewsResponseModel =
        withContext(dispatchers.io()) {
            try {
                val data =
                    apiService.getBreakingNews(countryCode = countryCode, category = category)
                newsResponseMapper.converterToNewsResponseModel(data)
            } catch (ex: Exception) {
                throw ex
            }
        }

    override suspend fun searchNews(searchQuery: String): NewsResponseModel =
        withContext(dispatchers.io()) {
            try {
                val data = apiService.searchForNews(searchQuery)
                newsResponseMapper.converterToNewsResponseModel(data)
            } catch (ex: Exception) {
                throw IllegalStateException(ex)
            }
        }
}
