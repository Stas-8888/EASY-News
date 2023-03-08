package com.example.newsapppp.domain.interactors.authentication

import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import com.example.newsapppp.domain.model.UserModel
import com.example.newsapppp.domain.repository.AuthenticationRepositoryContract
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(val repository: AuthenticationRepositoryContract) :
    BaseUseCaseSuspend<UserModel, Unit> {

    override suspend fun invoke(data: UserModel) {
        repository.forgotPassword(data)
    }
}
