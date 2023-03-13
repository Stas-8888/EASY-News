package com.example.newsapppp.domain.interactors.sharedpreferences

import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import javax.inject.Inject

class SaveCountryFlagUseCase @Inject constructor(private val repo: SharedPreferencesContract) :
    BaseUseCaseSuspend<String, Unit> {

    override suspend fun invoke(data: String) {
        repo.saveCountryFlag(data)
    }
}
