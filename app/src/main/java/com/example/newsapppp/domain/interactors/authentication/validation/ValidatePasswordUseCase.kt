package com.example.newsapppp.domain.interactors.authentication.validation

import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCase
import javax.inject.Inject

/**
 * This use case validates a password.
 * @param repository The validation repository used to validate the password.
 */
class ValidatePasswordUseCase @Inject constructor(val repository: ValidationRepositoryContract) :
    BaseUseCase<String, String> {

    /**
     * Invokes the use case and validates the specified password.
     * @param data The password to validate.
     * @return The validated password.
     */
    override fun invoke(data: String): String {
        return repository.validatePassword(data)
    }
}
