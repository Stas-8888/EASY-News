package com.example.newsapppp.domain.interactors.room

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.repository.BaseUseCaseSuspend
import com.example.newsapppp.domain.repository.DataBaseRepositoryContract
import javax.inject.Inject

class InsertArticleUseCase @Inject constructor(private val repo: DataBaseRepositoryContract) :
    BaseUseCaseSuspend<ArticleModel, Unit> {

    override suspend fun invoke(data: ArticleModel) {
        repo.insertArticle(data)
    }
}
