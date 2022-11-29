package com.example.newsapppp.data.repository

import com.example.newsapppp.core.DispatchersList
import com.example.newsapppp.data.db.NewsDao
import com.example.newsapppp.data.mapper.ArticleMapper
import com.example.newsapppp.data.mapper.NewsResponseMapper
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.repository.DbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DbRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
    private val mapper: ArticleMapper,
    private val newsResponseMapper: NewsResponseMapper,
    private val dispatchers: DispatchersList.Base
) : DbRepository {

    override suspend fun insertArticle(article: ArticleModel) = withContext(dispatchers.io()) {
        newsDao.insertArticle(mapper.mapFromEntity(article))
    }

    override suspend fun deleteArticle(article: ArticleModel) = withContext(dispatchers.io()) {
        newsDao.deleteArticle(mapper.mapFromEntity(article))
    }

    override suspend fun deleteAllArticle() = withContext(dispatchers.io()) {
        newsDao.deleteAllArticle()
    }

    override fun getAllArticles(): Flow<List<ArticleModel>> {
        return newsDao.getAllArticles().map { newsResponseMapper.articleDbToArticleModel(it) }
    }
}
