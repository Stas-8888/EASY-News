package com.example.newsapppp.domain.interactors.authentication

import com.example.newsapppp.core.FirebaseState
import com.example.newsapppp.domain.repository.AuthenticationRepositoryContract
import javax.inject.Inject

class SignUpUseCase @Inject constructor(val repo: AuthenticationRepositoryContract) {

    suspend fun signUp(
        user: String,
        email: String,
        password: String,
        result: (FirebaseState<String>) -> Unit
    ) {
        repo.signup(user, email, password, result)
    }
}
