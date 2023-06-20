package com.example.newsapppp.domain.use_case.articles.cache

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.base_use_case.BaseUseCaseSuspend
import com.example.newsapppp.domain.repository.ArticleCacheRepository
import javax.inject.Inject

/**
 * This use case deletes a single article from the article cache repository.
 * @param contract The repository that stores the articles.
 */
class DeleteArticleUseCase @Inject constructor(private val contract: ArticleCacheRepository) :
    BaseUseCaseSuspend<ArticleModel, Unit> {

    /**
     * Invokes the use case and deletes the specified article from the repository.
     * @param data The article to be deleted from the repository.
     */
    override suspend fun invoke(data: ArticleModel) = contract.deleteArticle(data)
}
