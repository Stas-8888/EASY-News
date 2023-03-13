package com.example.newsapppp.domain.interactors.articlecache

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import javax.inject.Inject

class InsertArticleUseCase @Inject constructor(private val repo: ArticleCacheContract) :
    BaseUseCaseSuspend<ArticleModel, Unit> {

    override suspend fun invoke(data: ArticleModel) {
        repo.insertArticle(data)
    }
}
