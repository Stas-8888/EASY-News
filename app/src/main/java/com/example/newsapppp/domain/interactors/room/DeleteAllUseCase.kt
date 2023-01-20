package com.example.newsapppp.domain.interactors.room

import com.example.newsapppp.domain.repository.BaseUseCaseSuspend
import com.example.newsapppp.domain.repository.DataBaseRepositoryContract
import javax.inject.Inject

class DeleteAllUseCase @Inject constructor(private val repo: DataBaseRepositoryContract) :
    BaseUseCaseSuspend<Unit, Unit> {

    override suspend fun invoke(data: Unit) {
        repo.deleteAllArticle()
    }
}
