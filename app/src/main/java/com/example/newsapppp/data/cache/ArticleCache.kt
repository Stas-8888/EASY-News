package com.example.newsapppp.data.cache

import com.example.newsapppp.core.dispatcher.DispatcherRepositoryContract
import com.example.newsapppp.data.cache.db.dao.ArticleDao
import com.example.newsapppp.data.mapper.EntityMapper
import com.example.newsapppp.data.mapper.NewsResponseMapper
import com.example.newsapppp.domain.interactors.articlecache.ArticleCacheContract
import com.example.newsapppp.domain.model.ArticleModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleCache @Inject constructor(
    private val articleDao: ArticleDao,
    private val mapper: EntityMapper,
    private val newsResponseMapper: NewsResponseMapper,
    private val dispatcher: DispatcherRepositoryContract
) : ArticleCacheContract {

    override suspend fun insertArticle(article: ArticleModel) = dispatcher.io {
        articleDao.insertArticle(mapper.mapToModel(article))
    }

    override suspend fun deleteArticle(article: ArticleModel) = dispatcher.io {
        articleDao.deleteArticle(mapper.mapToModel(article))
    }

    override suspend fun deleteAllArticle() = dispatcher.io {
        articleDao.deleteAllArticle()
    }

    override fun getAllArticles(): Flow<List<ArticleModel>> {
        return articleDao.getAllArticles()
            .map { newsResponseMapper.fromArticleEntityToArticleModel(it) }
    }
}
