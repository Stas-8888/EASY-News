package com.example.newsapppp.data.authentication

import android.util.Patterns
import com.example.newsapppp.R
import com.example.newsapppp.core.resources.ProvideResourcesContract
import com.example.newsapppp.domain.interactors.authentication.ValidationRepositoryContract
import javax.inject.Inject

class ValidationRepository @Inject constructor(
    private val provideResources: ProvideResourcesContract
) : ValidationRepositoryContract {

    override fun validateEmail(email: String): String {
        if (email.isEmpty()) {
            return provideResources.makeString(R.string.empty_email)
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return provideResources.makeString(R.string.wrong_email)
        }
        return provideResources.makeString(R.string.successful)
    }

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

    override fun validateRepeatedPassword(password: String, repeatedPassword: String): String {
        if (password != repeatedPassword) {
            return provideResources.makeString(R.string.different_password)
        }
        return provideResources.makeString(R.string.successful)
    }

    override fun fullName(fullName: String): String {
        if (fullName.length < 6) {
            return provideResources.makeString(R.string.minimum_character)
        }
        return provideResources.makeString(R.string.successful)
    }
}
