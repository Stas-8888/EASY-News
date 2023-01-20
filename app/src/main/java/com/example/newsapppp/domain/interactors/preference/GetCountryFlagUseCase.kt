package com.example.newsapppp.domain.interactors.preference

import com.example.newsapppp.domain.repository.BaseUseCase
import com.example.newsapppp.domain.repository.SharedPrefRepositoryContract

class GetCountryFlagUseCase(private val repo: SharedPrefRepositoryContract) : BaseUseCase<Unit, String> {

    override fun invoke(data: Unit): String {
        return repo.getCountryFlag()
    }
}
