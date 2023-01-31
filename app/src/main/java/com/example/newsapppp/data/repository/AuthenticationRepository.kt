package com.example.newsapppp.data.repository

import com.example.newsapppp.R
import com.example.newsapppp.core.DispatcherRepositoryContract
import com.example.newsapppp.core.ProvideResourcesContract
import com.example.newsapppp.domain.repository.AuthenticationRepositoryContract
import com.example.newsapppp.presentation.ui.authentication.forgotPassword.ForgotPasswordState
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val dispatcher: DispatcherRepositoryContract,
    private val provideResources: ProvideResourcesContract,
) : AuthenticationRepositoryContract {

    override suspend fun signIn(
        email: String,
        password: String,
    ): Task<AuthResult> = dispatcher.io {
        firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    override suspend fun signup(
        user: String,
        email: String,
        password: String,
    ): Task<AuthResult> = dispatcher.io {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
    }

    override suspend fun forgotPassword(
        email: String,
        result: (ForgotPasswordState<String>) -> Unit
    ) {
        dispatcher.io {
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    result.invoke(ForgotPasswordState.Failure(provideResources.string(R.string.email_sent)))
                }.addOnFailureListener {
                    result.invoke(ForgotPasswordState.Failure(it.message))
                }
        }
    }

    override fun logout() = firebaseAuth.signOut()
}
