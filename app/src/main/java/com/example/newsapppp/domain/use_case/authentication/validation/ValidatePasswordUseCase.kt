package com.example.newsapppp.domain.use_case.authentication.validation

import com.example.newsapppp.domain.base.BaseUseCase
import com.example.newsapppp.domain.repository.ValidationRepository
import javax.inject.Inject

/**
 * This use case validates a password.
 * @param contract The validation repository used to validate the password.
 */
class ValidatePasswordUseCase @Inject constructor(private val contract: ValidationRepository) :
    BaseUseCase<String, String> {

    /**
     * Invokes the use case and validates the specified password.
     * @param data The password to validate.
     * @return The validated password.
     */
    override fun invoke(data: String) = contract.validatePassword(data)
}
