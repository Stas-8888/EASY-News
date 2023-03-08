package com.example.newsapppp.domain.interactors.authentication.validation

import com.example.newsapppp.domain.repository.ValidationRepositoryContract
import javax.inject.Inject

class ValidateRepeatedPasswordUseCase @Inject constructor(val repository: ValidationRepositoryContract) {

    fun invoke(password: String, repeatedPassword: String): String {
        return repository.validateRepeatedPassword(password, repeatedPassword)
    }
}
