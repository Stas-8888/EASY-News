package com.example.newsapppp.domain.interactors

import com.example.newsapppp.domain.repository.BaseUseCaseSuspend
import com.example.newsapppp.domain.repository.SharedPrefRepository

class SaveCountryFlagUseCase(private val repo: SharedPrefRepository) :
    BaseUseCaseSuspend<String, Unit> {

    override suspend fun invoke(data: String) {
        repo.saveCountryFlag(data)
    }
}
