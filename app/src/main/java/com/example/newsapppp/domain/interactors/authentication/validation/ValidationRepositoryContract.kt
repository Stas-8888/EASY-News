package com.example.newsapppp.domain.interactors.authentication.validation

interface ValidationRepositoryContract {
    fun userName(fullName: String): String
    fun validateEmail(email: String): String
    fun validatePassword(passwordText: String): String
    fun validateRepeatedPassword(password: String, repeatedPassword: String): String
}
