package com.example.newsapppp.data.repository

import com.example.newsapppp.data.db.NewsDao
import com.example.newsapppp.data.mapper.ArticleMapper
import com.example.newsapppp.data.mapper.NewsResponseMapper
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.repository.DbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DbRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
    private val mapper: ArticleMapper,
    private val newsResponseMapper: NewsResponseMapper
) : DbRepository {

    override suspend fun insertArticle(article: ArticleModel) = withContext(Dispatchers.IO) {
        newsDao.insertArticle(mapper.mapArticleModelToArticleDb(article))
    }

    override suspend fun deleteArticle(article: ArticleModel) = withContext(Dispatchers.IO) {
        newsDao.deleteArticle(mapper.mapArticleModelToArticleDb(article))
    }

    override suspend fun deleteAllArticle() = withContext(Dispatchers.IO) {
        newsDao.deleteAllArticle()
    }

    override fun getAllArticles(): Flow<List<ArticleModel>> {
        return newsDao.getAllArticles().map { newsResponseMapper.articleDbToArticleModel(it) }
    }
}
