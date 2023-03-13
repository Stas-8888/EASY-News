package com.example.newsapppp.domain.interactors.articlecache

import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import javax.inject.Inject

class DeleteAllUseCase @Inject constructor(private val repo: ArticleCacheContract) :
    BaseUseCaseSuspend<Unit, Unit> {

    override suspend fun invoke(data: Unit) {
        repo.deleteAllArticle()
    }
}
