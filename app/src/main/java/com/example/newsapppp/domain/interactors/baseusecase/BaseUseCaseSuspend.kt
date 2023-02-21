package com.example.newsapppp.domain.interactors.baseusecase

interface BaseUseCaseSuspend<S, B> {

    suspend operator fun invoke(data: S): B
}
