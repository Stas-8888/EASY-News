package com.example.newsapppp.data.authentication

import com.example.newsapppp.common.dispatcher.DispatcherRepository
import com.example.newsapppp.domain.interactors.authentication.AuthenticationRepository
import com.example.newsapppp.domain.model.UserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

/**
 * This class provides the implementation for [AuthenticationRepository] interface. It
 * allows users to sign in, sign up, and reset their password using Firebase Authentication.
 * Additionally, it uses [DispatcherRepository] to perform asynchronous operations on
 * an I/O-bound coroutine dispatcher.
 *
 * @property firebaseAuth - The [FirebaseAuth] instance for authentication.
 * @property dispatcher - The [DispatcherRepository] instance to handle coroutine dispatching.
 */
class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val dispatcher: DispatcherRepository
) : AuthenticationRepository {

    /**
     * Asynchronously signs in a user with email and password credentials. Uses [dispatcher]
     * to perform the operation on an I/O-bound coroutine dispatcher.
     *
     * @param user - The [UserModel] object containing user's email and password.
     * @return A [Task] object representing the sign-in operation.
     */
    override suspend fun signIn(user: UserModel): Task<AuthResult> = dispatcher.io {
        firebaseAuth.signInWithEmailAndPassword(user.email, user.password)
    }

    /**
     * Asynchronously signs up a new user with email and password credentials. Uses [dispatcher]
     * to perform the operation on an I/O-bound coroutine dispatcher.
     *
     * @param user - The [UserModel] object containing user's email and password.
     * @return A [Task] object representing the sign-up operation.
     */
    override suspend fun signUp(user: UserModel): Task<AuthResult> = dispatcher.io {
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
    }

    /**
     * Asynchronously sends a password reset email to the user's email address. Uses [dispatcher]
     * to perform the operation on an I/O-bound coroutine dispatcher.
     *
     * @param user - The [UserModel] object containing user's email.
     * @return A [Task] object representing the password reset operation.
     */
    override suspend fun forgotPassword(user: UserModel): Task<Void> = dispatcher.io {
        firebaseAuth.sendPasswordResetEmail(user.email)
    }

    /**
     * Signs the user out of the app.
     */
    override fun logOut() = firebaseAuth.signOut()
}
