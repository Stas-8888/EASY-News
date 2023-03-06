package com.example.newsapppp.data.repository

import com.example.newsapppp.core.dispatcher.DispatcherRepositoryContract
import com.example.newsapppp.domain.model.UserModel
import com.example.newsapppp.domain.repository.AuthenticationRepositoryContract
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val dispatcher: DispatcherRepositoryContract
) : AuthenticationRepositoryContract {

    override suspend fun signIn(user: UserModel): Task<AuthResult> = dispatcher.io {
        firebaseAuth.signInWithEmailAndPassword(user.email, user.password)
    }

    override suspend fun signUp(user: UserModel): Task<AuthResult> = dispatcher.io {
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
    }

    override suspend fun forgotPassword(user: UserModel): Task<Void> = dispatcher.io {
        firebaseAuth.sendPasswordResetEmail(user.email)
    }

    override fun logOut() = firebaseAuth.signOut()
}
