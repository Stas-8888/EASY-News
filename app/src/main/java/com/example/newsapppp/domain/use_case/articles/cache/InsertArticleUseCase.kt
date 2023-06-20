package com.example.newsapppp.domain.use_case.articles.cache

import com.example.newsapppp.domain.base_use_case.BaseUseCaseSuspend
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.repository.ArticleCacheRepository
import javax.inject.Inject

/**
 * This use case inserts a single article into the article cache repository.
 * @param contract The repository that stores the articles.
 */
class InsertArticleUseCase @Inject constructor(private val contract: ArticleCacheRepository) :
    BaseUseCaseSuspend<ArticleModel, Unit> {

    /**
     * Invokes the use case and inserts the specified article into the repository.
     * @param data The article to be inserted into the repository.
     */
    override suspend fun invoke(data: ArticleModel) = contract.insertArticle(data)
}
