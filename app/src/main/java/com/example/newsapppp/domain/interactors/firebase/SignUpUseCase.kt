package com.example.newsapppp.domain.interactors.firebase

import com.example.newsapppp.core.FirebaseState
import com.example.newsapppp.domain.repository.FirebaseRepositoryContract
import javax.inject.Inject

class SignUpUseCase @Inject constructor(val repo: FirebaseRepositoryContract) {

    suspend fun signUp(
        user: String,
        email: String,
        password: String,
        result: (FirebaseState<String>) -> Unit
    ) {
        repo.signup(user, email, password, result)
    }
}
