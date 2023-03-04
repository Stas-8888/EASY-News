package com.example.newsapppp.domain.interactors.authentication

import com.example.newsapppp.domain.repository.AuthenticationRepositoryContract
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class SignUpUseCase @Inject constructor(val repo: AuthenticationRepositoryContract) {

    suspend fun signUp(user: String, email: String, password: String): Task<AuthResult> {
        return repo.signup(user, email, password)
    }
}
