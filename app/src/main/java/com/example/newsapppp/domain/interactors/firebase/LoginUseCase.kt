package com.example.newsapppp.domain.interactors.firebase

import com.example.newsapppp.core.FirebaseState
import com.example.newsapppp.domain.repository.FirebaseRepositoryContract

class LoginUseCase(val repo: FirebaseRepositoryContract) {

    suspend fun signIn(email: String, password: String, result: (FirebaseState<String>) -> Unit) {
        return repo.login(email, password, result)
    }
}
