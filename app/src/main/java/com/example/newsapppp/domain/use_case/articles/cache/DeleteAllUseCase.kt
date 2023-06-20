package com.example.newsapppp.domain.use_case.articles.cache

import com.example.newsapppp.domain.base_use_case.BaseUseCaseSuspend
import com.example.newsapppp.domain.repository.ArticleCacheRepository
import javax.inject.Inject

/**
 * This use case deletes all articles from the article cache repository.
 * @param contract The repository that stores the articles.
 */
class DeleteAllUseCase @Inject constructor(private val contract: ArticleCacheRepository) :
    BaseUseCaseSuspend<Unit, Unit> {

    /**
     * Invokes the use case and deletes all articles from the repository.
     * @param data This parameter is not used in this use case.
     */
    override suspend fun invoke(data: Unit) = contract.deleteAllArticle()
}