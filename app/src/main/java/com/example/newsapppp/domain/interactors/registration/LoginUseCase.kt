package com.example.newsapppp.domain.interactors.registration

import com.example.newsapppp.domain.repository.RegistrationRepository
import com.example.newsapppp.presentation.ui.registration.login.LoginState

class LoginUseCase(val repo: RegistrationRepository) {

    suspend fun signIn(
        email: String,
        password: String,
        navigateTo: () -> Unit,
        result: (LoginState) -> Unit

    ) {
        repo.login(email, password, navigateTo, result)
    }
}
