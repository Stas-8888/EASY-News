package com.example.newsapppp.domain.interactors.baseUseCase

interface BaseUseCaseSuspend<S, B> {

    suspend operator fun invoke(data: S): B
}
