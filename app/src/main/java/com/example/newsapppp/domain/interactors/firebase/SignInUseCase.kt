package com.example.newsapppp.domain.interactors.firebase

import com.example.newsapppp.core.FirebaseState
import com.example.newsapppp.domain.repository.FirebaseRepositoryContract
import javax.inject.Inject

class SignInUseCase @Inject constructor(val repo: FirebaseRepositoryContract) {

    suspend fun signIn(email: String, password: String, result: (FirebaseState<String>) -> Unit) {
        return repo.signIn(email, password, result)
    }
}