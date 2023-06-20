package com.example.newsapppp.data.repository.authentication

import android.util.Patterns
import com.example.newsapppp.R
import com.example.newsapppp.common.resources.ProvideResourcesRepository
import com.example.newsapppp.domain.repository.ValidationRepository
import javax.inject.Inject

/**
 * Repository class for validating user input during authentication.
 * @param provideResources An interface for accessing app resources.
 */
class ValidationRepositoryImpl @Inject constructor(
    private val provideResources: ProvideResourcesRepository
) : ValidationRepository {

    /**
     * Validates user input for email address.
     * @param email User input for email address.
     * @return A string indicating success or failure of the validation.
     */
    override fun validateEmail(email: String): String {
        if (email.isEmpty()) {
            return provideResources.makeString(R.string.empty_email)
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return provideResources.makeString(R.string.wrong_email)
        }
        return provideResources.makeString(R.string.successful)
    }

    /**
     * Validates user input for password.
     * @param passwordText User input for password.
     * @return A string indicating success or failure of the validation.
     */
    override fun validatePassword(passwordText: String): String {
        if (passwordText.isEmpty()) {
            return provideResources.makeString(R.string.empty_password)
        }
        if (passwordText.length < 6) {
            return provideResources.makeString(R.string.minimum_character)
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            return provideResources.makeString(R.string.uppercase)
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return provideResources.makeString(R.string.lowercase)
        }
        return provideResources.makeString(R.string.successful)
    }

    /**
     * Validates user input for repeated password.
     * @param password User input for password.
     * @param repeatedPassword User input for repeated password.
     * @return A string indicating success or failure of the validation.
     */
    override fun validateRepeatedPassword(password: String, repeatedPassword: String): String {
        if (password != repeatedPassword) {
            return provideResources.makeString(R.string.different_password)
        }
        return provideResources.makeString(R.string.successful)
    }

    /**
     * Validates user input for full name.
     * @param fullName User input for full name.
     * @return A string indicating success or failure of the validation.
     */
    override fun userName(fullName: String): String {
        if (fullName.length < 6) {
            return provideResources.makeString(R.string.minimum_character)
        }
        return provideResources.makeString(R.string.successful)
    }
}
