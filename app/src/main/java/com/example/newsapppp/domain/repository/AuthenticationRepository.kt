package com.example.newsapppp.domain.repository

import com.example.newsapppp.domain.model.UserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthenticationRepository {
    suspend fun signUp(user: UserModel): Task<AuthResult>
    suspend fun signIn(user: UserModel): Task<AuthResult>
    suspend fun forgotPassword(user: UserModel): Task<Void>
    fun logOut()
}
