package com.example.newsapppp.domain.use_case.authentication

import com.example.newsapppp.domain.base_use_case.BaseUseCaseSuspend
import com.example.newsapppp.domain.model.UserModel
import com.example.newsapppp.domain.repository.AuthenticationRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

/**
 * This use case signs in a user.
 * @param contract The authentication repository used to sign in the user.
 */
class SignInUseCase @Inject constructor(private val contract: AuthenticationRepository) :
    BaseUseCaseSuspend<UserModel, Task<AuthResult>> {

    /**
     * Invokes the use case and signs in the specified user.
     * @param data The user to sign in.
     * @return A task that resolves with an authentication result when the sign in is complete.
     */
    override suspend fun invoke(data: UserModel) = contract.signIn(data)
}
