package com.example.newsapppp.domain.interactors.registration

import com.example.newsapppp.data.repository.Resource
import com.example.newsapppp.domain.repository.RegistrationRepository
import com.google.firebase.auth.FirebaseUser

class SignUpUseCase(val repo: RegistrationRepository) {

    suspend fun signUp(
        user: String,
        email: String,
        password: String,
        navigateTo: Unit
    ): Resource<FirebaseUser> {
        return repo.signup(user, email, password, navigateTo)
    }
}
