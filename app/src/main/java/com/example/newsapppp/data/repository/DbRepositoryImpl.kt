package com.example.newsapppp.data.repository

import com.example.newsapppp.core.DispatchersList
import com.example.newsapppp.data.db.NewsDao
import com.example.newsapppp.data.mapper.ArticleMapper
import com.example.newsapppp.data.mapper.NewsResponseMapper
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.repository.DbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DbRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
    private val mapper: ArticleMapper,
    private val newsResponseMapper: NewsResponseMapper,
    private val dispatchers: DispatchersList
) : DbRepository {

    override suspend fun insertArticle(article: ArticleModel) = dispatchers.withContextIO {
        newsDao.insertArticle(mapper.mapFromEntity(article))
    }

    override suspend fun deleteArticle(article: ArticleModel) = dispatchers.withContextIO {
        newsDao.deleteArticle(mapper.mapFromEntity(article))
    }

    override suspend fun deleteAllArticle() = dispatchers.withContextIO {
        newsDao.deleteAllArticle()
    }

    override fun getAllArticles(): Flow<List<ArticleModel>> {
        return newsDao.getAllArticles().map { newsResponseMapper.articleDbToArticleModel(it) }
    }
}
