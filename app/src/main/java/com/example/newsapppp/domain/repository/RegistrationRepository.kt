package com.example.newsapppp.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface RegistrationRepository {

    suspend fun login(
        email: String,
        password: String
    ): Task<AuthResult>

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
