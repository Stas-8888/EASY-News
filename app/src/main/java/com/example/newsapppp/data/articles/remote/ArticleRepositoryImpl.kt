package com.example.newsapppp.data.articles.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapppp.core.Constants.MAX_PAGE_SIZE
import com.example.newsapppp.core.Constants.PAGE_SIZE
import com.example.newsapppp.core.dispatcher.DispatcherRepository
import com.example.newsapppp.data.articles.cache.SharedPreferencesRepositoryImpl
import com.example.newsapppp.data.articles.mapper.NewsResponseMapper
import com.example.newsapppp.data.articles.remote.paging.ArticlePagingSource
import com.example.newsapppp.data.articles.remote.service.ApiService
import com.example.newsapppp.domain.interactors.articles.remote.ArticleRepository
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.model.NewsResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * This is a remote repository for retrieving news article data. It uses the [ApiService] to fetch
 * article data from a remote API, and the [NewsResponseMapper] to map the API response to a
 * [NewsResponseModel]. Additionally, it uses the [SharedPreferencesRepositoryImpl] to get the user's country
 * flag and the [DispatcherRepository] to perform asynchronous operations on an I/O-bound
 * coroutine dispatcher.
 *
 * This class implements the [ArticleRepository] interface and provides methods to fetch
 * articles based on a category and to search for articles based on a search query.
 */
class ArticleRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: NewsResponseMapper,
    private val countryCode: SharedPreferencesRepositoryImpl,
    private val dispatcher: DispatcherRepository,
) : ArticleRepository {

    /**
     * Retrieves a [Flow] of [PagingData] for the specified [category] of news articles from the
     * remote data source. Uses [dispatcher] to perform the operation on an I/O-bound coroutine
     * dispatcher.
     *
     * @param category - the category of news articles to retrieve.
     * @return a [Flow] of [PagingData] objects that represent the articles.
     */
    override fun fetchedArticles(category: String): Flow<PagingData<ArticleModel>> {
        val pagingConfig =
            PagingConfig(pageSize = PAGE_SIZE, maxSize = MAX_PAGE_SIZE, enablePlaceholders = false)
        val pagingSource =
            { ArticlePagingSource(apiService, countryCode, category, mapper) }
        return Pager(config = pagingConfig, pagingSourceFactory = pagingSource).flow
            .flowOn(Dispatchers.IO)
    }

    /**
     * Searches the remote data source for news articles that match the specified [searchQuery].
     * Uses [dispatcher] to perform the operation on an I/O-bound coroutine dispatcher.
     *
     * @param searchQuery - the search query string to use when searching for articles.
     * @return a [NewsResponseModel] object that contains the search results.
     */
    override suspend fun searchArticles(searchQuery: String): NewsResponseModel = dispatcher.io {
        val data = apiService.searchArticles(searchQuery)
        mapper.converterToNewsResponseModel(data)
    }
}