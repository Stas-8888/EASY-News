package com.example.newsapppp.domain.interactors.firebase

import com.example.newsapppp.core.FirebaseState
import com.example.newsapppp.domain.repository.FirebaseRepositoryContract
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(val repository: FirebaseRepositoryContract) {

    suspend fun forgotPassword(email: String, result: (FirebaseState<String>) -> Unit) {
        repository.forgotPassword(email, result)
    }

}
