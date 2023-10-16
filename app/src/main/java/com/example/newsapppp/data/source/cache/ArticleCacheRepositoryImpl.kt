package com.example.newsapppp.data.source.cache

import com.example.newsapppp.common.dispatcher.DispatcherHelper
import com.example.newsapppp.data.source.cache.db.dao.ArticleDao
import com.example.newsapppp.data.mapper.EntityMapper
import com.example.newsapppp.data.mapper.NewsResponseMapper
import com.example.newsapppp.domain.repository.ArticleCacheRepository
import com.example.newsapppp.domain.model.ArticleModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * This is a cache repository for storing and retrieving news article data. It uses the [ArticleDao]
 * to interact with the local database, the [EntityMapper] to map the article data between the domain
 * layer and the database layer, the [NewsResponseMapper] to map the article data between the remote
 * data source and the domain layer, and the [DispatcherHelper] to perform asynchronous
 * operations on an I/O-bound coroutine dispatcher.
 */
class ArticleCacheRepositoryImpl @Inject constructor(
    private val articleDao: ArticleDao,
    private val mapper: EntityMapper,
    private val newsResponseMapper: NewsResponseMapper,
    private val dispatcher: DispatcherHelper
) : ArticleCacheRepository {

    /**
     * Inserts the specified [article] into the cache. Uses [dispatcher] to perform the operation
     * on an I/O-bound coroutine dispatcher.
     *
     * @param article the article to insert into the cache.
     */
    override suspend fun insertArticle(article: ArticleModel) = dispatcher.io {
        articleDao.insertArticle(mapper.mapToModel(article))
    }

    /**
     * Deletes the specified [article] from the cache. Uses [dispatcher] to perform the operation
     * on an I/O-bound coroutine dispatcher.
     *
     * @param article the article to delete from the cache.
     */
    override suspend fun deleteArticle(article: ArticleModel) = dispatcher.io {
        articleDao.deleteArticle(mapper.mapToModel(article))
    }

    /**
     * Deletes all articles from the cache. Uses [dispatcher] to perform the operation on an I/O-bound
     * coroutine dispatcher.
     */
    override suspend fun deleteAllArticle() = dispatcher.io {
        articleDao.deleteAllArticle()
    }

    /**
     * Retrieves all articles from the cache as a [Flow] of [List] of [ArticleModel]. Uses
     * [dispatcher] to perform the operation on an I/O-bound coroutine dispatcher.
     *
     * @return a [Flow] of [List] of [ArticleModel] objects that represent the articles.
     */
    override fun getAllArticles(): Flow<List<ArticleModel>> {
        return articleDao.getAllArticles()
            .map { newsResponseMapper.fromArticleEntityToArticleModel(it) }
    }
}
