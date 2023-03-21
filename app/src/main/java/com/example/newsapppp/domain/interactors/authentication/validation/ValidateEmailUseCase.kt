package com.example.newsapppp.domain.interactors.authentication.validation

import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCase
import javax.inject.Inject

/**
 * This use case validates an email address.
 * @param repository The validation repository used to validate the email address.
 */
class ValidateEmailUseCase @Inject constructor(val repository: ValidationRepositoryContract) :
    BaseUseCase<String, String> {

    /**
     * Invokes the use case and validates the specified email address.
     * @param data The email address to validate.
     * @return The validated email address.
     */
    override fun invoke(data: String): String {
        return repository.validateEmail(data)
    }
}
