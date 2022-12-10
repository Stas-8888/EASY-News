package com.example.newsapppp.domain.repository

import com.example.newsapppp.data.repository.Resource
import com.google.firebase.auth.FirebaseUser

interface RegistrationRepository {

    val currentUser: FirebaseUser?

    suspend fun login(email: String, password: String, navigateTo: () -> Unit)

    suspend fun signup(
        user: String,
        email: String,
        password: String,
        navigateTo: Unit
    ): Resource<FirebaseUser>

    fun logout()

    fun fullName(fullName: String): String
    fun validateEmail(email: String): String
    fun validatePassword(passwordText: String): String
    fun validateRepeatedPassword(password: String, repeatedPassword: String): String
}
