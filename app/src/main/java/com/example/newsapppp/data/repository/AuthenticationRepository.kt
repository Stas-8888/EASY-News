package com.example.newsapppp.data.repository

import com.example.newsapppp.R
import com.example.newsapppp.core.DispatcherRepositoryContract
import com.example.newsapppp.core.ProvideResourcesContract
import com.example.newsapppp.domain.repository.AuthenticationRepositoryContract
import com.example.newsapppp.presentation.ui.authentication.signin.SignInState
import com.example.newsapppp.presentation.ui.authentication.forgotPassword.ForgotPasswordState
import com.example.newsapppp.presentation.ui.authentication.signup.SignUpState
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
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
    ): Task<AuthResult> = dispatcher.io {
        firebaseAuth.signInWithEmailAndPassword(email, password)
    }


    override suspend fun signup(
        user: String,
        email: String,
        password: String,
        result: (SignUpState<String>) -> Unit
    ) {
        dispatcher.io {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    result.invoke(SignUpState.Success(provideResources.string(R.string.successfully_register)))
                }
                .addOnFailureListener {
                    when (it) {
                        is FirebaseAuthWeakPasswordException -> result.invoke(failureSignUp(R.string.password_lengs_6))
                        is FirebaseAuthInvalidCredentialsException -> result.invoke(failureSignUp(R.string.invalid_email))
                        is FirebaseAuthUserCollisionException -> result.invoke(failureSignUp(R.string.email_registered))
                        else -> result.invoke(failureSignUp(R.string.invalid_authentication))
                    }
                }
        }
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

    private fun failure(message: Int): SignInState<String> {
        return SignInState.Failure(provideResources.string(message))
    }

    private fun failureSignUp(message: Int): SignUpState<String> {
        return SignUpState.Failure(provideResources.string(message))
    }

    private fun success(message: Int): SignInState<String> {
        return SignInState.Success(provideResources.string(message))
    }
}
