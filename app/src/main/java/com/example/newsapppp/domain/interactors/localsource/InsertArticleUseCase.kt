package com.example.newsapppp.domain.interactors.localsource

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import com.example.newsapppp.domain.repository.ArticleLocalSourceRepositoryContract
import javax.inject.Inject

class InsertArticleUseCase @Inject constructor(private val repo: ArticleLocalSourceRepositoryContract) :
    BaseUseCaseSuspend<ArticleModel, Unit> {

    override suspend fun invoke(data: ArticleModel) {
        repo.insertArticle(data)
    }
}
