package com.example.newsapppp.domain.interactors.preference

import com.example.newsapppp.domain.repository.BaseUseCaseSuspend
import com.example.newsapppp.domain.repository.SharedPrefRepositoryContract
import javax.inject.Inject

class SaveCountryFlagUseCase @Inject constructor(private val repo: SharedPrefRepositoryContract) :
    BaseUseCaseSuspend<String, Unit> {

    override suspend fun invoke(data: String) {
        repo.saveCountryFlag(data)
    }
}
