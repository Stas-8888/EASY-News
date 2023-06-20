package com.example.newsapppp.domain.base

interface BaseUseCaseSuspend<S, B> {

    suspend operator fun invoke(data: S): B
}
