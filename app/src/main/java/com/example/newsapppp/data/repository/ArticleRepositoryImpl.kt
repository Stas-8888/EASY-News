package com.example.newsapppp.data.repository

import com.example.newsapppp.data.db.NewsDao
import com.example.newsapppp.data.mapper.ArticleMapper
import com.example.newsapppp.data.mapper.NewsResponseMapper
import com.example.newsapppp.data.network.ApiService
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.model.NewsResponseModel
import com.example.newsapppp.domain.repository.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao,
    private val mapper: ArticleMapper,
    private val newsResponseMapper: NewsResponseMapper
) : ArticleRepository {

    override suspend fun insert(article: ArticleModel) = withContext(Dispatchers.IO) {
        newsDao.insert(mapper.mapArticleModelToArticleDb(article))
    }

    override suspend fun delete(article: ArticleModel) = withContext(Dispatchers.IO) {
        newsDao.deleteArticle(mapper.mapArticleModelToArticleDb(article))
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        newsDao.deleteAll()
    }

    override fun getAllArticles(): Flow<List<ArticleModel>> {
        return newsDao.getAllArticles().map { newsResponseMapper.articleDbToArticleModel(it) }
    }

    override suspend fun getNews(countryCode: String, category: String): NewsResponseModel =
        withContext(Dispatchers.IO) {
            try {
                val data =
                    apiService.getBreakingNews(countryCode = countryCode, category = category)
                newsResponseMapper.converterToNewsResponseModel(data)
            } catch (ex: Exception) {
                TODO()
            }
        }

    override suspend fun searchNews(searchQuery: String, pageNumber: Int): NewsResponseModel =
        withContext(Dispatchers.IO) {
            try {
                val data = apiService.searchForNews(searchQuery, pageNumber)
                newsResponseMapper.converterToNewsResponseModel(data)
            } catch (ex: Exception) {
                TODO()
            }
        }
}
