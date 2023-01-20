package com.example.newsapppp.domain.interactors.firebase.validation

import android.content.Context
import android.util.Patterns
import com.example.newsapppp.R
import com.example.newsapppp.core.ManageResources
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ValidationRepositoryContract {
    fun fullName(fullName: String): String
    fun validateEmail(email: String): String
    fun validatePassword(passwordText: String): String
    fun validateRepeatedPassword(password: String, repeatedPassword: String): String
}

class ValidationRepository @Inject constructor(
    @ApplicationContext var context: Context,
    private val manageResources: ManageResources
    ) : ValidationRepositoryContract {

    override fun validateEmail(email: String): String {
        if (email.isNullOrEmpty()) {
            return "Enter email address"
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return context.getString(R.string.InvalidEmail)
        }
        return context.getString(R.string.successful)
    }

    override fun validatePassword(passwordText: String): String {
        if (passwordText.isNullOrEmpty()) {
            return "Empty password"
        }
        if (passwordText.length < 6) {
            return context.getString(R.string.MinimumCharacter)
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            return context.getString(R.string.Uppercase)
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return context.getString(R.string.Lowercase)
        }
        return manageResources.string(R.string.successful)
    }

    override fun validateRepeatedPassword(password: String, repeatedPassword: String): String {
        if (password != repeatedPassword) {
            return "Different Password"
        }
        return context.getString(R.string.successful)
    }

    override fun fullName(fullName: String): String {
        if (fullName.length < 6) {
            return context.getString(R.string.MinimumCharacter)
        }
        return context.getString(R.string.successful)
    }
}
