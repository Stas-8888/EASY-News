package com.example.newsapppp.data.repository

import com.example.newsapppp.R
import com.example.newsapppp.core.Dispatchers
import com.example.newsapppp.core.FirebaseState
import com.example.newsapppp.domain.repository.FirebaseRepositoryContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val dispatchers: Dispatchers
) : FirebaseRepositoryContract {

    override suspend fun signIn(
        email: String,
        password: String,
        result: (FirebaseState<String>) -> Unit
    ) {
        dispatchers.io {
//            if (email.isNullOrEmpty() && password.isNullOrEmpty()) {
//                result.invoke(FirebaseState.Failure("Empty failed, Check email and password"))
//            } else {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            result.invoke(FirebaseState.Success("Login successfully!"))
                            result.invoke(FirebaseState.Navigate(R.id.mainFragment))
                        }
                    }.addOnFailureListener {
                        result.invoke(FirebaseState.Failure("Authentication failed, Check email and password"))
                    }
                    .addOnCanceledListener {
                        result.invoke(FirebaseState.Failure("Email or Password is empty"))
                    }
//            }
        }
    }

    override suspend fun signup(
        user: String,
        email: String,
        password: String,
        result: (FirebaseState<String>) -> Unit
    ) {
        dispatchers.io {
            if (email.isEmpty() && password.isEmpty()) {
                result.invoke(FirebaseState.Failure("Email or Password is empty"))
            } else {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            result.invoke(FirebaseState.Success("User register successfully!"))
                            result.invoke(FirebaseState.Navigate(R.id.loginFragment))
                        } else {
                            try {
                                throw it.exception ?: java.lang.Exception("Invalid authentication")
                            } catch (e: FirebaseAuthWeakPasswordException) {
                                result.invoke(FirebaseState.Failure("Authentication failed, Password should be at least 6 characters"))
                            } catch (e: FirebaseAuthInvalidCredentialsException) {
                                result.invoke(FirebaseState.Failure("Authentication failed, Invalid email entered"))
                            } catch (e: FirebaseAuthUserCollisionException) {
                                result.invoke(FirebaseState.Failure("Authentication failed, Email already registered."))
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
                    result.invoke(FirebaseState.Success("Email has been sent"))

                } else {
                    result.invoke(FirebaseState.Failure(task.exception?.message))
                }
            }.addOnFailureListener {
                result.invoke(FirebaseState.Failure("Authentication failed, Check email"))
            }
    }
}
