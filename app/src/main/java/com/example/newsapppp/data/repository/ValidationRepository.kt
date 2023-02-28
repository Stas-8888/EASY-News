package com.example.newsapppp.data.repository

import android.util.Patterns
import com.example.newsapppp.R
import com.example.newsapppp.core.resources.ProvideResourcesContract
import com.example.newsapppp.domain.repository.ValidationRepositoryContract
import javax.inject.Inject

class ValidationRepository @Inject constructor(
    private val manageResources: ProvideResourcesContract
) : ValidationRepositoryContract {

    override fun validateEmail(email: String): String {
        if (email.isEmpty()) {
            return manageResources.string(R.string.empty_email)
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return manageResources.string(R.string.wrong_email)
        }
        return manageResources.string(R.string.successful)
    }

    override fun validatePassword(passwordText: String): String {
        if (passwordText.isEmpty()) {
            return manageResources.string(R.string.empty_password)
        }
        if (passwordText.length < 6) {
            return manageResources.string(R.string.minimum_character)
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            return manageResources.string(R.string.uppercase)
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return manageResources.string(R.string.lowercase)
        }
        return manageResources.string(R.string.successful)
    }

    override fun validateRepeatedPassword(password: String, repeatedPassword: String): String {
        if (password != repeatedPassword) {
            return manageResources.string(R.string.different_password)
        }
        return manageResources.string(R.string.successful)
    }

    override fun fullName(fullName: String): String {
        if (fullName.length < 6) {
            return manageResources.string(R.string.minimum_character)
        }
        return manageResources.string(R.string.successful)
    }
}
