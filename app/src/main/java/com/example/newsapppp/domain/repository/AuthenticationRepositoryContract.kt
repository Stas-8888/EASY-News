package com.example.newsapppp.domain.repository

import com.example.newsapppp.core.FirebaseState

interface AuthenticationRepositoryContract {

    suspend fun signIn(
        email: String,
        password: String,
        result: (FirebaseState<String>) -> Unit
    )

    suspend fun signup(
        user: String,
        email: String,
        password: String,
        result: (FirebaseState<String>) -> Unit
    )

    fun logout()

    suspend fun forgotPassword(email: String, result: (FirebaseState<String>) -> Unit)

}
