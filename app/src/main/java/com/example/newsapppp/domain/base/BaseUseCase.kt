package com.example.newsapppp.domain.base

interface BaseUseCase<in Parameter, out Result> {

    operator fun invoke(data: Parameter): Result
}
