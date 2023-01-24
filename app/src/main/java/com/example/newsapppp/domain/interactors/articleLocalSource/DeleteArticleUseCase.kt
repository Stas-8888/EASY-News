package com.example.newsapppp.domain.interactors.articleLocalSource

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.interactors.baseUseCase.BaseUseCaseSuspend
import com.example.newsapppp.domain.repository.DataBaseRepositoryContract
import javax.inject.Inject

class DeleteArticleUseCase @Inject constructor(private val repo: DataBaseRepositoryContract) :
    BaseUseCaseSuspend<ArticleModel, Unit> {

    override suspend fun invoke(data: ArticleModel) {
        repo.deleteArticle(data)
    }
}
