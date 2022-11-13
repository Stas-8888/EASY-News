package com.example.newsapppp.domain.interactors

import com.example.newsapppp.domain.repository.BaseUseCaseSuspend
import com.example.newsapppp.domain.repository.SharedPrefRepository

class SaveSwitchPositionUseCase(private val repo: SharedPrefRepository) :
    BaseUseCaseSuspend<Boolean, Unit> {

    override suspend fun invoke(data: Boolean) {
        repo.saveSwitchPosition(data)
    }
}
