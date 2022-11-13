package com.example.newsapppp.domain.interactors

import com.example.newsapppp.domain.repository.BaseUseCaseSuspend
import com.example.newsapppp.domain.repository.DbRepository

class DeleteAllUseCase(private val repo: DbRepository) :
    BaseUseCaseSuspend<Unit, Unit> {

    override suspend fun invoke(data: Unit) {
        repo.deleteAllArticle()
    }
}
