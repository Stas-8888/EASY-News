package com.example.newsapppp.domain.repository

import com.example.newsapppp.presentation.ui.authentication.AuthState

interface AuthenticationRepositoryContract {

    suspend fun signIn(
        email: String,
        password: String,
        result: (AuthState<String>) -> Unit
    )

    suspend fun signup(
        user: String,
        email: String,
        password: String,
        result: (AuthState<String>) -> Unit
    )

    fun logout()

    suspend fun forgotPassword(email: String, result: (AuthState<String>) -> Unit)

}
