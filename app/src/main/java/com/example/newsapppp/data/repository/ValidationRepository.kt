package com.example.newsapppp.data.repository

import android.util.Patterns
import com.example.newsapppp.R
import com.example.newsapppp.core.ManageResourcesContract
import com.example.newsapppp.domain.repository.ValidationRepositoryContract
import javax.inject.Inject

class ValidationRepository @Inject constructor(
    private val manageResourcesContract: ManageResourcesContract
) : ValidationRepositoryContract {

    override fun validateEmail(email: String): String {
        if (email.isEmpty()) {
            return manageResourcesContract.string(R.string.empty_email)
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return manageResourcesContract.string(R.string.InvalidEmail)
        }
        return manageResourcesContract.string(R.string.successful)
    }

    override fun validatePassword(passwordText: String): String {
        if (passwordText.isEmpty()) {
            return manageResourcesContract.string(R.string.empty_password)
        }
        if (passwordText.length < 6) {
            return manageResourcesContract.string(R.string.MinimumCharacter)
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            return manageResourcesContract.string(R.string.Uppercase)
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return manageResourcesContract.string(R.string.Lowercase)
        }
        return manageResourcesContract.string(R.string.successful)
    }

    override fun validateRepeatedPassword(password: String, repeatedPassword: String): String {
        if (password != repeatedPassword) {
            return manageResourcesContract.string(R.string.defferent_password)
        }
        return manageResourcesContract.string(R.string.successful)
    }

    override fun fullName(fullName: String): String {
        if (fullName.length < 6) {
            return manageResourcesContract.string(R.string.MinimumCharacter)
        }
        return manageResourcesContract.string(R.string.successful)
    }
}
