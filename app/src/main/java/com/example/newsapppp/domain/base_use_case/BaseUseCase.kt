package com.example.newsapppp.domain.base_use_case

interface BaseUseCase<in Parameter, out Result> {

    operator fun invoke(data: Parameter): Result
}
