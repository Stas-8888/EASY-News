package com.example.newsapppp.domain.interactors.articleremote

import com.example.newsapppp.domain.model.NewsResponseModel
import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import javax.inject.Inject

/**
 * This use case searches articles from a remote data source based on a query.
 * @param contract The repository that searches for the articles.
 */
class SearchArticlesUseCase @Inject constructor(private val contract: ArticleRemoteContract) :
    BaseUseCaseSuspend<String, NewsResponseModel> {

    /**
     * Invokes the use case and searches for articles based on the specified query.
     * @param data The query to search for.
     * @return A news response model containing the search results.
     */
    override suspend fun invoke(data: String) = contract.searchArticles(data)
}
