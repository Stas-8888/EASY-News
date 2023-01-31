package com.example.newsapppp.domain.repository

import com.example.newsapppp.presentation.ui.authentication.signin.SignInState
import com.example.newsapppp.presentation.ui.authentication.forgotPassword.ForgotPasswordState
import com.example.newsapppp.presentation.ui.authentication.signup.SignUpState

interface AuthenticationRepositoryContract {

    suspend fun signIn(
        email: String,
        password: String,
        result: (SignInState<String>) -> Unit
    )

    suspend fun signup(
        user: String,
        email: String,
        password: String,
        result: (SignUpState<String>) -> Unit
    )

    fun logout()

    suspend fun forgotPassword(email: String, result: (ForgotPasswordState<String>) -> Unit)
}
