package com.example.newsapppp.domain.repository

import com.example.newsapppp.core.FirebaseState

interface FirebaseRepositoryContract {

    suspend fun signIn(
        email: String,
        password: String,
        result: (FirebaseState<String>) -> Unit
    )

    suspend fun signup(
        user: String,
        email: String,
        password: String,
        navigateTo: Unit
    )

    fun logout()

    fun fullName(fullName: String): String
    fun validateEmail(email: String): String
    fun validatePassword(passwordText: String): String
    fun validateRepeatedPassword(password: String, repeatedPassword: String): String
}
