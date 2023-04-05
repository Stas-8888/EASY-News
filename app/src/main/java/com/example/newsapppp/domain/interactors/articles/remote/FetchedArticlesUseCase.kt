package com.example.newsapppp.domain.interactors.articles.remote

import androidx.paging.PagingData
import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCase
import com.example.newsapppp.domain.model.ArticleModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * This use case fetches a paged list of articles from a remote data source.
 * @param contract The repository that fetches the articles.
 */
class FetchedArticlesUseCase @Inject constructor(private val contract: ArticleRemoteContract) :
    BaseUseCase<String, Flow<PagingData<ArticleModel>>> {

    /**
     * Invokes the use case and fetches a paged list of articles from the remote data source.
     * @param data The category of articles to be fetched.
     * @return A flow of paged article data from the remote data source.
     */
    override fun invoke(data: String) = contract.fetchedArticles(category = data)
}
