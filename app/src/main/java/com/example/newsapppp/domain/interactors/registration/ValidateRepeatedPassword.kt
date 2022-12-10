package com.example.newsapppp.domain.interactors.registration

import com.example.newsapppp.domain.repository.RegistrationRepository

class ValidateRepeatedPasswordUseCase(val repo: RegistrationRepository) {

    fun validateRepeatedPassword(password: String, repeatedPassword: String): String {
        return repo.validateRepeatedPassword(password, repeatedPassword)
    }

//    fun execute(password: String, repeatedPassword: String): ValidationResult {
//        if(password != repeatedPassword) {
//            return ValidationResult(
//                successful = false,
//                errorMessage = "The passwords don't match"
//            )
//        }
//        return ValidationResult(
//            successful = true
//        )
//    }
}
