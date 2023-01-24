package com.example.newsapppp.domain.interactors.authentication

import com.example.newsapppp.presentation.ui.authentication.AuthState
import com.example.newsapppp.domain.repository.AuthenticationRepositoryContract
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(val repository: AuthenticationRepositoryContract) {

    suspend fun forgotPassword(email: String, result: (AuthState<String>) -> Unit) {
        repository.forgotPassword(email, result)
    }

}
