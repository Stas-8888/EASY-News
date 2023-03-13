package com.example.newsapppp.domain.interactors.sharedpreferences

import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import javax.inject.Inject

class SaveSwitchPositionUseCase @Inject constructor(private val repo: SharedPreferencesContract) :
    BaseUseCaseSuspend<Boolean, Unit> {

    override suspend fun invoke(data: Boolean) {
        repo.saveSwitchPosition(data)
    }
}
