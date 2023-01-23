package com.example.newsapppp.domain.repository

import android.util.Patterns
import com.example.newsapppp.R
import com.example.newsapppp.core.ManageResources
import javax.inject.Inject

interface ValidationRepositoryContract {
    fun fullName(fullName: String): String
    fun validateEmail(email: String): String
    fun validatePassword(passwordText: String): String
    fun validateRepeatedPassword(password: String, repeatedPassword: String): String
}

class ValidationRepository @Inject constructor(
    private val manageResources: ManageResources
) : ValidationRepositoryContract {

    override fun validateEmail(email: String): String {
        if (email.isNullOrEmpty()) {
            return manageResources.string(R.string.empty_email)
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return manageResources.string(R.string.InvalidEmail)
        }
        return manageResources.string(R.string.successful)
    }

    override fun validatePassword(passwordText: String): String {
        if (passwordText.isNullOrEmpty()) {
            return manageResources.string(R.string.empty_password)
        }
        if (passwordText.length < 6) {
            return manageResources.string(R.string.MinimumCharacter)
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            return manageResources.string(R.string.Uppercase)
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return manageResources.string(R.string.Lowercase)
        }
        return manageResources.string(R.string.successful)
    }

    override fun validateRepeatedPassword(password: String, repeatedPassword: String): String {
        if (password != repeatedPassword) {
            return manageResources.string(R.string.defferent_password)
        }
        return manageResources.string(R.string.successful)
    }

    override fun fullName(fullName: String): String {
        if (fullName.length < 6) {
            return manageResources.string(R.string.MinimumCharacter)
        }
        return manageResources.string(R.string.successful)
    }
}
