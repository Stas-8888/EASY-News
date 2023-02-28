package com.example.newsapppp.data.repository

import com.example.newsapppp.core.dispatcher.DispatcherRepositoryContract
import com.example.newsapppp.data.db.NewsDao
import com.example.newsapppp.data.mapper.EntityMapper
import com.example.newsapppp.data.mapper.NewsResponseMapper
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.repository.ArticleLocalSourceRepositoryContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleLocalSourceRepository @Inject constructor(
    private val newsDao: NewsDao,
    private val mapper: EntityMapper,
    private val newsResponseMapper: NewsResponseMapper,
    private val dispatcher: DispatcherRepositoryContract
) : ArticleLocalSourceRepositoryContract {

    override suspend fun insertArticle(article: ArticleModel) = dispatcher.io {
        newsDao.insertArticle(mapper.mapToModel(article))
    }

    override suspend fun deleteArticle(article: ArticleModel) = dispatcher.io {
        newsDao.deleteArticle(mapper.mapToModel(article))
    }

    override suspend fun deleteAllArticle() = dispatcher.io {
        newsDao.deleteAllArticle()
    }

    override fun getAllArticles(): Flow<List<ArticleModel>> {
        return newsDao.getAllArticles().map { newsResponseMapper.fromArticleEntityToArticleModel(it) }
    }
}
