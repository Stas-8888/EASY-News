package com.example.newsapppp.data.repository

import com.example.newsapppp.R
import com.example.newsapppp.core.DispatcherRepositoryContract
import com.example.newsapppp.core.ProvideResourcesContract
import com.example.newsapppp.domain.repository.AuthenticationRepositoryContract
import com.example.newsapppp.presentation.ui.authentication.AuthState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val dispatcher: DispatcherRepositoryContract,
    private val provideResources: ProvideResourcesContract,
) : AuthenticationRepositoryContract {

    override suspend fun signIn(
        email: String,
        password: String,
        result: (AuthState<String>) -> Unit
    ) {
        dispatcher.io {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    result.invoke(success(R.string.successfully_sign_in))
                    result.invoke(AuthState.Navigate(R.id.mainFragment))
                }.addOnFailureListener {
                    result.invoke(failure(R.string.authentication_failed))
                }
        }
    }

    override suspend fun signup(
        user: String,
        email: String,
        password: String,
        result: (AuthState<String>) -> Unit
    ) {
        dispatcher.io {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    result.invoke(success(R.string.successfully_register))
                    result.invoke(AuthState.Navigate(R.id.loginFragment))
                }
                .addOnFailureListener {
                    when (it) {
                        is FirebaseAuthWeakPasswordException -> result.invoke(failure(R.string.password_lengs_6))
                        is FirebaseAuthInvalidCredentialsException -> result.invoke(failure(R.string.invalid_email))
                        is FirebaseAuthUserCollisionException -> result.invoke(failure(R.string.email_registered))
                        else -> result.invoke(failure(R.string.invalid_authentication))
                    }
                }
        }
    }

    override suspend fun forgotPassword(
        email: String,
        result: (AuthState<String>) -> Unit
    ) {
        dispatcher.io {
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    result.invoke(success(R.string.email_sent))
                    result.invoke(AuthState.Navigate(R.id.loginFragment))
                }.addOnFailureListener {
                    result.invoke(failure(R.string.incorrect_email))
                    result.invoke(AuthState.Failure(it.message))
                }
        }
    }

    override fun logout() = firebaseAuth.signOut()

    private fun failure(message: Int): AuthState<String> {
        return AuthState.Failure(provideResources.string(message))
    }

    private fun success(message: Int): AuthState<String> {
        return AuthState.Success(provideResources.string(message))
    }
}
