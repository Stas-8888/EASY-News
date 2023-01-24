package com.example.newsapppp.domain.interactors.preference

import com.example.newsapppp.domain.interactors.baseUseCase.BaseUseCaseSuspend
import com.example.newsapppp.domain.repository.SharedPrefRepositoryContract
import javax.inject.Inject

class SaveSwitchPositionUseCase @Inject constructor(private val repo: SharedPrefRepositoryContract) :
    BaseUseCaseSuspend<Boolean, Unit> {

    override suspend fun invoke(data: Boolean) {
        repo.saveSwitchPosition(data)
    }
}
