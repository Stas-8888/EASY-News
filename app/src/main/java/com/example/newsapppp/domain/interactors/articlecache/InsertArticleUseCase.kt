package com.example.newsapppp.domain.interactors.articlecache

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import javax.inject.Inject

/**
 * This use case inserts a single article into the article cache repository.
 * @param repo The repository that stores the articles.
 */
class InsertArticleUseCase @Inject constructor(private val repo: ArticleCacheContract) :
    BaseUseCaseSuspend<ArticleModel, Unit> {

    /**
     * Invokes the use case and inserts the specified article into the repository.
     * @param data The article to be inserted into the repository.
     */
    override suspend fun invoke(data: ArticleModel) {
        repo.insertArticle(data)
    }
}
