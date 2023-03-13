package com.example.newsapppp.domain.interactors.authentication

import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import com.example.newsapppp.domain.model.UserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class SignUpUseCase @Inject constructor(val repo: AuthenticationRepositoryContract) :
    BaseUseCaseSuspend<UserModel, Task<AuthResult>> {

    override suspend fun invoke(data: UserModel): Task<AuthResult> {
        return repo.signUp(data)
    }
}
