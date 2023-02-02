package com.example.newsapppp.domain.repository

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

    fun logOut()

    suspend fun forgotPassword(email: String): Task<Void>
}
