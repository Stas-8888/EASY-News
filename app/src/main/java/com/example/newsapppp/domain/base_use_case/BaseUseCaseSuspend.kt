package com.example.newsapppp.domain.base_use_case

interface BaseUseCaseSuspend<S, B> {

    suspend operator fun invoke(data: S): B
}
