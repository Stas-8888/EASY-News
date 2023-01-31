package com.example.newsapppp.domain.interactors.authentication

import com.example.newsapppp.domain.repository.AuthenticationRepositoryContract
import com.example.newsapppp.presentation.ui.authentication.signup.SignUpState
import javax.inject.Inject

class SignUpUseCase @Inject constructor(val repo: AuthenticationRepositoryContract) {

    suspend fun signUp(
        user: String,
        email: String,
        password: String,
        result: (SignUpState<String>) -> Unit
    ) {
        repo.signup(user, email, password, result)
    }
}
