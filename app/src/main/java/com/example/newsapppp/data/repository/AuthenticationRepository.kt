package com.example.newsapppp.data.repository

import com.example.newsapppp.R
import com.example.newsapppp.core.DispatcherRepositoryContract
import com.example.newsapppp.core.ManageResourcesContract
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
    private val manageResources: ManageResourcesContract,
) : AuthenticationRepositoryContract {

    override suspend fun signIn(
        email: String,
        password: String,
        result: (AuthState<String>) -> Unit
    ) {
        dispatcher.io {
            when {
                email.isEmpty() -> result.invoke(failure(R.string.empty_email))
                password.isEmpty() -> result.invoke(failure(R.string.empty_password))
                else -> {
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            result.invoke(success(R.string.successfully_sign_in))
                            result.invoke(AuthState.Navigate(R.id.mainFragment))
                        }.addOnFailureListener {
                            result.invoke(failure(R.string.authentication_failed))
                        }
                }
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
            when {
                email.isEmpty() -> result.invoke(failure(R.string.empty_email))
                password.isEmpty() -> result.invoke(failure(R.string.empty_password))
                else -> {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                result.invoke(success(R.string.successfully_register))
                                result.invoke(AuthState.Navigate(R.id.loginFragment))
                            } else {
                                try {
                                    throw it.exception
                                        ?: java.lang.Exception(manageResources.string(R.string.invalid_authentication))
                                } catch (e: FirebaseAuthWeakPasswordException) {
                                    result.invoke(failure(R.string.password_lengs_6))
                                } catch (e: FirebaseAuthInvalidCredentialsException) {
                                    result.invoke(failure(R.string.invalid_email))
                                } catch (e: FirebaseAuthUserCollisionException) {
                                    result.invoke(failure(R.string.email_registered))
                                } catch (e: Exception) {
                                    result.invoke(AuthState.Failure(e.message))
                                }
                            }
                        }
                        .addOnFailureListener {
                            result.invoke(AuthState.Failure(it.localizedMessage))
                        }
                }
            }
        }
    }

    override fun logout() = firebaseAuth.signOut()

    override suspend fun forgotPassword(
        email: String,
        result: (AuthState<String>) -> Unit
    ) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result.invoke(success(R.string.email_sent))
                    result.invoke(AuthState.Navigate(R.id.loginFragment))
                } else {
                    result.invoke(AuthState.Failure(task.exception?.message))
                }
            }.addOnFailureListener {
                result.invoke(failure(R.string.incorrect_email))
            }
    }

    private fun failure(message: Int): AuthState<String> {
        return AuthState.Failure(manageResources.string(message))
    }

    private fun success(message: Int): AuthState<String> {
        return AuthState.Success(manageResources.string(message))
    }
}
