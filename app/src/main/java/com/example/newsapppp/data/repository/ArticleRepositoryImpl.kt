package com.example.newsapppp.data.repository

import com.example.newsapppp.data.mapper.NewsResponseMapper
import com.example.newsapppp.data.network.ApiService
import com.example.newsapppp.domain.model.NewsResponseModel
import com.example.newsapppp.domain.repository.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val newsResponseMapper: NewsResponseMapper
) : ArticleRepository {

    override suspend fun getNews(countryCode: String, category: String): NewsResponseModel =
        withContext(Dispatchers.IO) {
            try {
                val data =
                    apiService.getBreakingNews(countryCode = countryCode, category = category)
                newsResponseMapper.converterToNewsResponseModel(data)
            } catch (ex: Exception) {
                throw ex
            }
        }

    override suspend fun searchNews(searchQuery: String): NewsResponseModel =
        withContext(Dispatchers.IO) {
            try {
                val data = apiService.searchForNews(searchQuery)
                newsResponseMapper.converterToNewsResponseModel(data)
            } catch (ex: Exception) {
                throw ex
            }
        }
}
