package com.example.newsapppp.domain.interactors.authentication

import com.example.newsapppp.core.FirebaseState
import com.example.newsapppp.domain.repository.AuthenticationRepositoryContract
import javax.inject.Inject

class SignInUseCase @Inject constructor(val repository: AuthenticationRepositoryContract) {

    suspend fun signIn(email: String, password: String, result: (FirebaseState<String>) -> Unit) {
        return repository.signIn(email, password, result)
    }
}
