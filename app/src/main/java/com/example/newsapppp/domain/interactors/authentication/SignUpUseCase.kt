package com.example.newsapppp.domain.interactors.authentication

import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import com.example.newsapppp.domain.model.UserModel
import javax.inject.Inject

class SignUpUseCase @Inject constructor(val repo: AuthenticationRepositoryContract) :
    BaseUseCaseSuspend<UserModel, Unit> {

    override suspend fun invoke(data: UserModel) {
        repo.signUp(data)
    }
}
