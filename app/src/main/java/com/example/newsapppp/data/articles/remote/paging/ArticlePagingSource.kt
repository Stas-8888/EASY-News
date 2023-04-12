package com.example.newsapppp.data.articles.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapppp.data.articles.cache.SharedPreferences
import com.example.newsapppp.data.articles.mapper.NewsResponseMapper
import com.example.newsapppp.data.articles.remote.service.ApiService
import com.example.newsapppp.domain.model.ArticleModel
import retrofit2.HttpException

private const val STARTING_PAGE_INDEX = 1

/**
 * A paging source for fetching news articles from an API.
 * @param repository An instance of ApiService for fetching articles.
 * @param countryCode A country code used to filter articles by country.
 * @param category A category used to filter articles by topic.
 * @param mapper A mapper used to map the response data to ArticleModel objects.
 */
class ArticlePagingSource(
    private val repository: ApiService,
    private val countryCode: SharedPreferences,
    private val category: String,
    private val mapper: NewsResponseMapper
) : PagingSource<Int, ArticleModel>() {

    /**
     * Load a page of articles from the API.
     * @param params The load parameters, including the page key and page size.
     * @return A LoadResult object containing the loaded data, as well as keys for the previous and next pages.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleModel> {
        return try {
            val currentPage = params.key ?: STARTING_PAGE_INDEX
            val response = repository.fetchedArticles(currentPage, countryCode.getCountryFlag(), category)
            val data = response.articles.filter { it.urlToImage != null && it.title != null }
            val responseData = mutableListOf<ArticleModel>()
            responseData.addAll(mapper.mapToListArticleRemote(data))

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == STARTING_PAGE_INDEX) null else -1,
                nextKey = currentPage.plus(STARTING_PAGE_INDEX)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    /**
     * Get the key for the next page to be loaded after a refresh.
     * @param state The current paging state.
     * @return The key for the next page to be loaded.
     */
    override fun getRefreshKey(state: PagingState<Int, ArticleModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(STARTING_PAGE_INDEX)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(STARTING_PAGE_INDEX)
        }
    }
}
