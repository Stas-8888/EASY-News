package com.example.newsapppp.domain.interactors.firebase

import com.example.newsapppp.domain.repository.FirebaseRepositoryContract

class ValidateRepeatedPasswordUseCase(val repo: FirebaseRepositoryContract) {

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
