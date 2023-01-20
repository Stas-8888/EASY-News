package com.example.newsapppp.domain.interactors.firebase

import com.example.newsapppp.core.FirebaseState
import com.example.newsapppp.domain.repository.FirebaseRepositoryContract
import javax.inject.Inject

class SignInUseCase @Inject constructor(val repository: FirebaseRepositoryContract) {

    suspend fun signIn(email: String, password: String, result: (FirebaseState<String>) -> Unit) {
        return repository.signIn(email, password, result)
    }
}
