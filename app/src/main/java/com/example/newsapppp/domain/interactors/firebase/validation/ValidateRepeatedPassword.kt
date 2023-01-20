package com.example.newsapppp.domain.interactors.firebase.validation

import javax.inject.Inject

class ValidateRepeatedPasswordUseCase @Inject constructor(val repository: ValidationRepositoryContract) {

    fun validateRepeatedPassword(password: String, repeatedPassword: String): String {
        return repository.validateRepeatedPassword(password, repeatedPassword)
    }
}
