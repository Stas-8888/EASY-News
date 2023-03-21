package com.example.newsapppp.domain.interactors.authentication.validation

import javax.inject.Inject

/**
 * This use case validates that a password and its repeated value match.
 * @param repository The validation repository used to validate the password.
 */
class ValidateRepeatedPasswordUseCase @Inject constructor(val repository: ValidationRepositoryContract) {

    /**
     * Invokes the use case and validates that the specified password and its repeated value match.
     * @param password The password to validate.
     * @param repeatedPassword The repeated password to validate.
     * @return The validated repeated password as a string.
     */
    fun invoke(password: String, repeatedPassword: String): String {
        return repository.validateRepeatedPassword(password, repeatedPassword)
    }
}
