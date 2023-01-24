package com.example.newsapppp.domain.interactors.baseUseCase

interface BaseUseCase<in Parameter, out Result> {

    operator fun invoke(data: Parameter): Result
}
