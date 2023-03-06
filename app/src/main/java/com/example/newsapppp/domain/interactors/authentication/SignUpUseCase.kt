package com.example.newsapppp.domain.interactors.authentication

import com.example.newsapppp.domain.model.UserModel
import com.example.newsapppp.domain.repository.AuthenticationRepositoryContract
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class SignUpUseCase @Inject constructor(val repo: AuthenticationRepositoryContract) {

    suspend fun signUp(user: UserModel): Task<AuthResult> {
        return repo.signUp(user)
    }
}
