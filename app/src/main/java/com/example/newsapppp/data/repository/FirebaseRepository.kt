package com.example.newsapppp.data.repository

import android.content.Context
import android.util.Patterns
import com.example.newsapppp.R
import com.example.newsapppp.core.DispatchersList
import com.example.newsapppp.core.ManageResources
import com.example.newsapppp.core.FirebaseState
import com.example.newsapppp.domain.repository.FirebaseRepositoryContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val manageResources: ManageResources,
    private val dispatchers: DispatchersList,
    @ApplicationContext var context: Context
) : FirebaseRepositoryContract {

    override suspend fun signIn(
        email: String,
        password: String,
        result: (FirebaseState<String>) -> Unit
    ) {
        dispatchers.io {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        result.invoke(FirebaseState.Success("Login successfully!"))
                        result.invoke(FirebaseState.Navigate(R.id.mainFragment))
                    }
                }.addOnFailureListener {
                    result.invoke(FirebaseState.Failure("Authentication failed, Check email and password"))
                }
        }
    }

    override suspend fun signup(
        user: String,
        email: String,
        password: String,
        result: (FirebaseState<String>) -> Unit
    ) {
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
                result.invoke(
                    FirebaseState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }


//    override suspend fun signup(
//        user: String,
//        email: String,
//        password: String,
//        navigateTo: Unit
//    ) {
//        try {
//            firebaseAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        navigateTo
//                    } else {
//                        Toast.makeText(context, "Wrong Email or Password", Toast.LENGTH_SHORT)
//                            .show()
//                    }
//                }
//        } catch (e: Exception) {
//            Toast.makeText(context, "Email or Password", Toast.LENGTH_SHORT).show()
//        }
//    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override fun validateEmail(email: String): String {
        if (email.isNullOrEmpty()) {
            return "Enter email address"
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return context.getString(R.string.InvalidEmail)
        }
        return context.getString(R.string.successful)
    }

    override fun validatePassword(passwordText: String): String {
        if (passwordText.isNullOrEmpty()) {
            return "Enter password"
        }
        if (passwordText.length < 6) {
            return context.getString(R.string.MinimumCharacter)
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            return context.getString(R.string.Uppercase)
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return context.getString(R.string.Lowercase)
        }
        return manageResources.string(R.string.successful)
    }

    override fun validateRepeatedPassword(password: String, repeatedPassword: String): String {
        if (password != repeatedPassword) {
            return "Different Password"
        }
        return context.getString(R.string.successful)
    }

    override fun fullName(fullName: String): String {
        if (fullName.length < 6) {
            return context.getString(R.string.MinimumCharacter)
        }
        return context.getString(R.string.successful)
    }
}
