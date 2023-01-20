package com.example.newsapppp.domain.interactors.preference

import com.example.newsapppp.domain.repository.BaseUseCaseSuspend
import com.example.newsapppp.domain.repository.SharedPrefRepositoryContract

class SaveSwitchPositionUseCase(private val repo: SharedPrefRepositoryContract) :
    BaseUseCaseSuspend<Boolean, Unit> {

    override suspend fun invoke(data: Boolean) {
        repo.saveSwitchPosition(data)
    }
}
