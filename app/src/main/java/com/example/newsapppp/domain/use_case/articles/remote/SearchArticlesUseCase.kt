package com.example.newsapppp.domain.use_case.articles.remote

import com.example.newsapppp.domain.model.NewsResponseModel
import com.example.newsapppp.domain.base_use_case.BaseUseCaseSuspend
import com.example.newsapppp.domain.repository.ArticleRepository
import javax.inject.Inject

/**
 * This use case searches articles from a remote data source based on a query.
 * @param contract The repository that searches for the articles.
 */
class SearchArticlesUseCase @Inject constructor(private val contract: ArticleRepository) :
    BaseUseCaseSuspend<String, NewsResponseModel> {

    /**
     * Invokes the use case and searches for articles based on the specified query.
     * @param data The query to search for.
     * @return A news response model containing the search results.
     */
    override suspend fun invoke(data: String) = contract.searchArticles(data)
}
