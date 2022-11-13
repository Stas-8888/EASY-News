package com.example.newsapppp.domain.repository

interface BaseUseCase<S, B> {

    operator fun invoke(data: S): B
}
