package com.example.newsapppp.domain.use_case.articles.remote

import androidx.paging.PagingData
import com.example.newsapppp.domain.base.BaseUseCase
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * This use case fetches a paged list of articles from a remote data source.
 * @param articleRepository The repository that fetches the articles.
 */
class FetchedArticlesUseCase @Inject constructor(private val articleRepository: ArticleRepository) :
    BaseUseCase<String, Flow<PagingData<ArticleModel>>> {

    /**
     * Invokes the use case and fetches a paged list of articles from the remote data source.
     * @param data The category of articles to be fetched.
     * @return A flow of paged article data from the remote data source.
     */
    override fun invoke(data: String) = articleRepository.fetchedArticles(category = data)
}
