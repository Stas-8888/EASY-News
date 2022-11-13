package com.example.newsapppp.domain.repository

interface BaseUseCaseSuspend<S, B> {

    suspend operator fun invoke(data: S): B
}
