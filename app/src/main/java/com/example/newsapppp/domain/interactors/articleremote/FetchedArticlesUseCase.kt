package com.example.newsapppp.domain.interactors.articleremote

import androidx.paging.PagingData
import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import com.example.newsapppp.domain.model.ArticleModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * This use case fetches a paged list of articles from a remote data source.
 * @param repo The repository that fetches the articles.
 */
class FetchedArticlesUseCase @Inject constructor(private val repo: ArticleRemoteContract) :
    BaseUseCaseSuspend<String, Flow<PagingData<ArticleModel>>> {

    /**
     * Invokes the use case and fetches a paged list of articles from the remote data source.
     * @param data The category of articles to be fetched.
     * @return A flow of paged article data from the remote data source.
     */
    override suspend fun invoke(data: String): Flow<PagingData<ArticleModel>> {
        return repo.fetchedArticles(category = data)
    }
}
