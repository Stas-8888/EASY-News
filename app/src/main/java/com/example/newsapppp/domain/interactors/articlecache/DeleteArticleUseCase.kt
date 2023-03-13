package com.example.newsapppp.domain.interactors.articlecache

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import javax.inject.Inject

class DeleteArticleUseCase @Inject constructor(private val repo: ArticleCacheContract) :
    BaseUseCaseSuspend<ArticleModel, Unit> {

    override suspend fun invoke(data: ArticleModel) {
        repo.deleteArticle(data)
    }
}
