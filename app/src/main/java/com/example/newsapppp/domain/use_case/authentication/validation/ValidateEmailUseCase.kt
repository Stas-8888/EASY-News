package com.example.newsapppp.domain.use_case.authentication.validation

import com.example.newsapppp.domain.base.BaseUseCase
import com.example.newsapppp.domain.repository.ValidationRepository
import javax.inject.Inject

/**
 * This use case validates an email address.
 * @param contract The validation repository used to validate the email address.
 */
class ValidateEmailUseCase @Inject constructor(private val contract: ValidationRepository) :
    BaseUseCase<String, String> {

    /**
     * Invokes the use case and validates the specified email address.
     * @param data The email address to validate.
     * @return The validated email address.
     */
    override fun invoke(data: String) = contract.validateEmail(data)
}
