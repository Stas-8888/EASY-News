package com.example.newsapppp.domain.use_case.articles.cache

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.base_use_case.BaseUseCase
import com.example.newsapppp.domain.repository.ArticleCacheRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * This use case retrieves a list of all articles from the article cache repository.
 * @param contract The repository that stores the articles.
 */
class ArticleAllCacheUseCase @Inject constructor(private val contract: ArticleCacheRepository) :
    BaseUseCase<Unit, Flow<List<ArticleModel>>> {

    /**
     * Invokes the use case and returns a flow of all articles.
     * @param data This parameter is not used in this use case.
     * @return A flow of all articles from the repository.
     */
    override fun invoke(data: Unit) = contract.getAllArticles()
}
