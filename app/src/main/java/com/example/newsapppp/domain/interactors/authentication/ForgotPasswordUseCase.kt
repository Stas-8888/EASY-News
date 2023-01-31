package com.example.newsapppp.domain.interactors.authentication

import com.example.newsapppp.domain.repository.AuthenticationRepositoryContract
import com.google.android.gms.tasks.Task
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(val repository: AuthenticationRepositoryContract) {

    suspend fun forgotPassword(email: String): Task<Void> {
        return repository.forgotPassword(email)
    }
}
