package com.example.newsapppp.domain.repository

interface ValidationRepositoryContract {
    fun fullName(fullName: String): String
    fun validateEmail(email: String): String
    fun validatePassword(passwordText: String): String
    fun validateRepeatedPassword(password: String, repeatedPassword: String): String
}
