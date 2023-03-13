package com.example.newsapppp.domain.interactors.authentication

import com.example.newsapppp.domain.model.UserModel

interface AuthenticationRepositoryContract {
    suspend fun signUp(user: UserModel)
    suspend fun signIn(user: UserModel)
    suspend fun forgotPassword(user: UserModel)
    fun logOut()
}
