package com.example.newsapppp.domain.interactors.registration

import com.example.newsapppp.domain.repository.RegistrationRepository
import com.example.newsapppp.presentation.ui.registration.login.LoginState
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class LoginUseCase(val repo: RegistrationRepository) {

    suspend fun signIn(
        email: String,
        password: String

    ): Task<AuthResult> {
        return repo.login(email, password)
    }
}
