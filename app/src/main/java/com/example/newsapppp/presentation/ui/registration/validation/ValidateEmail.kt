package com.example.newsapppp.presentation.ui.registration.validation

import android.util.Patterns
import com.example.newsapppp.R

object Validator {
    fun isValidEmail(email: String): Int {
        isEmpty(email)
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return R.string.InvalidEmail
        }
        return R.string.successful
    }

    fun isEmpty(email: String): Int {
        if (email.isEmpty()) {
            return R.string.empty_email
        }
        return R.string.successful
    }

}
