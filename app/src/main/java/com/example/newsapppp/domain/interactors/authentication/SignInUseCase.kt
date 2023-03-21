package com.example.newsapppp.domain.interactors.authentication

import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import com.example.newsapppp.domain.model.UserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

/**
 * This use case signs in a user.
 * @param repository The authentication repository used to sign in the user.
 */
class SignInUseCase @Inject constructor(val repository: AuthenticationRepositoryContract) :
    BaseUseCaseSuspend<UserModel, Task<AuthResult>> {

    /**
     * Invokes the use case and signs in the specified user.
     * @param data The user to sign in.
     * @return A task that resolves with an authentication result when the sign in is complete.
     */
    override suspend fun invoke(data: UserModel): Task<AuthResult> {
        return repository.signIn(data)
    }
}
