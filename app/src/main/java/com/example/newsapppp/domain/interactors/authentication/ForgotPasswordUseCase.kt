package com.example.newsapppp.domain.interactors.authentication

import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import com.example.newsapppp.domain.model.UserModel
import com.google.android.gms.tasks.Task
import javax.inject.Inject

/**
 * This use case resets a user's password.
 * @param contract The authentication repository used to reset the password.
 */
class ForgotPasswordUseCase @Inject constructor(val contract: AuthenticationRepositoryContract) :
    BaseUseCaseSuspend<UserModel, Task<Void>> {

    /**
     * Invokes the use case and resets the password for the specified user.
     * @param data The user whose password is being reset.
     * @return A task that resolves when the password reset is complete.
     */
    override suspend fun invoke(data: UserModel) = contract.forgotPassword(data)
}
