package com.example.newsapppp.domain.interactors.authentication.validation

import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCase
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(val repository: ValidationRepositoryContract) :
    BaseUseCase<String, String> {

    override fun invoke(data: String): String {
        return repository.validateEmail(data)
    }
}
