package com.example.newsapppp.data.repository

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import com.example.newsapppp.R
import com.example.newsapppp.data.utils.await
import com.example.newsapppp.domain.repository.RegistrationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    @ApplicationContext var context: Context
) : RegistrationRepository {

    override val currentUser: FirebaseUser? = firebaseAuth.currentUser

    override suspend fun login(
        email: String,
        password: String,
        navigateTo: () -> Unit
    ) {
        try {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navigateTo()
                } else {
                    Toast.makeText(context, "Wrong Email or Password", Toast.LENGTH_SHORT).show()
                }
            }

            } catch (e: Exception) {
            Toast.makeText(context, "Email or Password", Toast.LENGTH_SHORT).show()
        }
    }

    override suspend fun signup(
        user: String,
        email: String,
        password: String,
        navigateTo: Unit
    ): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful)
                        navigateTo
                }.await()
            result.user?.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(user).build()
            )?.await()
            return Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override fun validateEmail(email: String): String {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return context.getString(R.string.InvalidEmail)
        }
        return context.getString(R.string.successful)
    }

    override fun validatePassword(passwordText: String): String {
        if (passwordText.length < 6) {
            return context.getString(R.string.MinimumCharacter)
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            return context.getString(R.string.Uppercase)
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return context.getString(R.string.Lowercase)
        }
        return context.getString(R.string.successful)
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
