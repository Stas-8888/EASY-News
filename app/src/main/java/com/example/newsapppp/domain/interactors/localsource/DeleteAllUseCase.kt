package com.example.newsapppp.domain.interactors.localsource

import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import com.example.newsapppp.domain.repository.ArticleLocalSourceRepositoryContract
import javax.inject.Inject

class DeleteAllUseCase @Inject constructor(private val repo: ArticleLocalSourceRepositoryContract) :
    BaseUseCaseSuspend<Unit, Unit> {

    override suspend fun invoke(data: Unit) {
        repo.deleteAllArticle()
    }
}
