package com.example.newsapppp.domain.interactors.authentication

import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import com.example.newsapppp.domain.model.UserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

/**
 * This use case signs up a user.
 * @param contract The authentication repository used to sign up the user.
 */
class SignUpUseCase @Inject constructor(private val contract: AuthenticationRepositoryContract) :
    BaseUseCaseSuspend<UserModel, Task<AuthResult>> {

    /**
     * Invokes the use case and signs up the specified user.
     * @param data The user to sign up.
     * @return A task that resolves with an authentication result when the sign up is complete.
     */
    override suspend fun invoke(data: UserModel) = contract.signUp(data)
}
