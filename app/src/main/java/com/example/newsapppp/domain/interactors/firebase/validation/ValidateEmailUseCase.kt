package com.example.newsapppp.domain.interactors.firebase.validation

import com.example.newsapppp.domain.repository.BaseUseCase
import com.example.newsapppp.domain.repository.ValidationRepositoryContract
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(val repository: ValidationRepositoryContract) :
    BaseUseCase<String, String> {

    override fun invoke(data: String): String {
        return repository.validateEmail(data)
    }
}
