package com.example.newsapppp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapppp.data.mapper.NewsResponseMapper
import com.example.newsapppp.data.network.service.ApiService
import com.example.newsapppp.domain.model.ArticleModel
import retrofit2.HttpException

class ArticlePagingSource(
    private val repository: ApiService,
    private val countryCode: String,
    private val category: String,
    private val mapper: NewsResponseMapper
) : PagingSource<Int, ArticleModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleModel> {
        return try {
            val currentPage = params.key ?: 1
            val response = repository.getBreakingNews(currentPage, countryCode, category)
            val data = response.articles
            val responseData = mutableListOf<ArticleModel>()
            responseData.addAll(mapper.mapToListArticleRemote(data))

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, ArticleModel>): Int? {
        return null
    }
}
