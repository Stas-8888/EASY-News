package com.example.newsapppp.data.repository

import com.example.newsapppp.R
import com.example.newsapppp.core.DispatcherRepositoryContract
import com.example.newsapppp.core.FirebaseState
import com.example.newsapppp.core.ManageResourcesContract
import com.example.newsapppp.domain.repository.AuthenticationRepositoryContract
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
        result: (FirebaseState<String>) -> Unit
    ) {
        dispatcher.io {
            if (email.isEmpty() && password.isEmpty()) {
                result.invoke(FirebaseState.Failure(manageResources.string(R.string.empty_email_password)))
            } else if (email.isEmpty()) {
                result.invoke(FirebaseState.Failure(manageResources.string(R.string.empty_email)))
            } else if (password.isEmpty()) {
                result.invoke(FirebaseState.Failure(manageResources.string(R.string.empty_password)))
            } else {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            result.invoke(FirebaseState.Success(manageResources.string(R.string.successfully_sign_in)))
                            result.invoke(FirebaseState.Navigate(R.id.mainFragment))
                        }
                    }.addOnFailureListener {
                        result.invoke(FirebaseState.Failure((manageResources.string(R.string.authentication_failed))))
                    }
            }
        }
    }

    override suspend fun signup(
        user: String,
        email: String,
        password: String,
        result: (FirebaseState<String>) -> Unit
    ) {
        dispatcher.io {
            if (email.isEmpty() && password.isEmpty()) {
                result.invoke(FirebaseState.Failure(manageResources.string(R.string.empty_email_password)))
            } else if (email.isEmpty()) {
                result.invoke(FirebaseState.Failure(manageResources.string(R.string.empty_email)))
            } else if (password.isEmpty()) {
                result.invoke(FirebaseState.Failure(manageResources.string(R.string.empty_password)))
            } else {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            result.invoke(FirebaseState.Success(manageResources.string(R.string.successfully_register)))
                            result.invoke(FirebaseState.Navigate(R.id.loginFragment))
                        } else {
                            try {
                                throw it.exception
                                    ?: java.lang.Exception(manageResources.string(R.string.invalid_authentication))
                            } catch (e: FirebaseAuthWeakPasswordException) {
                                result.invoke(FirebaseState.Failure(manageResources.string(R.string.password_lengs_6)))
                            } catch (e: FirebaseAuthInvalidCredentialsException) {
                                result.invoke(FirebaseState.Failure(manageResources.string(R.string.invalid_email)))
                            } catch (e: FirebaseAuthUserCollisionException) {
                                result.invoke(FirebaseState.Failure(manageResources.string(R.string.email_registered)))
                            } catch (e: Exception) {
                                result.invoke(FirebaseState.Failure(e.message))
                            }
                        }
                    }
                    .addOnFailureListener {
                        result.invoke(FirebaseState.Failure(it.localizedMessage))
                    }
            }
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override suspend fun forgotPassword(email: String, result: (FirebaseState<String>) -> Unit) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result.invoke(FirebaseState.Success(manageResources.string(R.string.email_sent)))
                    result.invoke(FirebaseState.Navigate(R.id.loginFragment))
                } else {
                    result.invoke(FirebaseState.Failure(task.exception?.message))
                }
            }.addOnFailureListener {
                result.invoke(FirebaseState.Failure(manageResources.string(R.string.incorrect_email)))
            }
    }
}
