package com.example.newsapppp.domain.interactors.baseusecase

interface BaseUseCase<in Parameter, out Result> {

    operator fun invoke(data: Parameter): Result
}
