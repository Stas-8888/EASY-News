package com.example.newsapppp.domain.interactors.authentication

import com.example.newsapppp.domain.repository.AuthenticationRepositoryContract
import com.example.newsapppp.presentation.ui.authentication.signin.SignInState
import javax.inject.Inject

class SignInUseCase @Inject constructor(val repository: AuthenticationRepositoryContract) {

    suspend fun signIn(email: String, password: String, result: (SignInState<String>) -> Unit) {
        return repository.signIn(email, password, result)
    }
}
