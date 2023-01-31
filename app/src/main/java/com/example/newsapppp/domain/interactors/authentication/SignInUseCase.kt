package com.example.newsapppp.domain.interactors.authentication

import com.example.newsapppp.domain.repository.AuthenticationRepositoryContract
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class SignInUseCase @Inject constructor(val repository: AuthenticationRepositoryContract) {

    suspend fun signIn(email: String, password: String): Task<AuthResult> {
        return repository.signIn(email, password)
    }
}
