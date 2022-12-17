package com.example.newsapppp.domain.interactors.registration

import com.example.newsapppp.domain.repository.RegistrationRepository

class SignUpUseCase(val repo: RegistrationRepository) {

    suspend fun signUp(
        user: String,
        email: String,
        password: String,
        navigateTo: Unit
    ) {
        repo.signup(user, email, password, navigateTo)
    }
}
