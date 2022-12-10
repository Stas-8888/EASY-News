package com.example.newsapppp.domain.interactors.registration

import com.example.newsapppp.domain.repository.RegistrationRepository

class LoginUseCase(val repo: RegistrationRepository) {

    suspend fun signIn(
        email: String,
        password: String,
        navigateTo: () -> Unit
    ) {
         repo.login(email, password, navigateTo)
    }
}
