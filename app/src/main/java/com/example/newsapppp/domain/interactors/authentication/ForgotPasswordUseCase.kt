package com.example.newsapppp.domain.interactors.authentication

import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import com.example.newsapppp.domain.model.UserModel
import com.google.android.gms.tasks.Task
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(val repository: AuthenticationRepositoryContract) :
    BaseUseCaseSuspend<UserModel, Task<Void>> {

    override suspend fun invoke(data: UserModel): Task<Void> {
        return repository.forgotPassword(data)
    }
}
