package com.example.newsapppp.domain.repository

interface BaseUseCase<in Parameter, out Result> {

    operator fun invoke(data: Parameter): Result
}
