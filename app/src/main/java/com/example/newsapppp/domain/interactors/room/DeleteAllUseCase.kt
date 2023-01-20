package com.example.newsapppp.domain.interactors.room

import com.example.newsapppp.domain.repository.BaseUseCaseSuspend
import com.example.newsapppp.domain.repository.DataBaseRepositoryContract

class DeleteAllUseCase(private val repo: DataBaseRepositoryContract) :
    BaseUseCaseSuspend<Unit, Unit> {

    override suspend fun invoke(data: Unit) {
        repo.deleteAllArticle()
    }
}
