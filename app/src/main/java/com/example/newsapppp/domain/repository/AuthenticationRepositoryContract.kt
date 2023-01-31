package com.example.newsapppp.domain.repository

import com.example.newsapppp.presentation.ui.authentication.forgotPassword.ForgotPasswordState
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthenticationRepositoryContract {

    suspend fun signIn(
        email: String,
        password: String,
    ): Task<AuthResult>

    suspend fun signup(
        user: String,
        email: String,
        password: String,
    ): Task<AuthResult>

    fun logout()

    suspend fun forgotPassword(email: String, result: (ForgotPasswordState<String>) -> Unit)
}
