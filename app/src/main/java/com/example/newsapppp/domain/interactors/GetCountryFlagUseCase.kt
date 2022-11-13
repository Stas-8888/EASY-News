package com.example.newsapppp.domain.interactors

import com.example.newsapppp.domain.repository.BaseUseCase
import com.example.newsapppp.domain.repository.SharedPrefRepository

class GetCountryFlagUseCase(private val repo: SharedPrefRepository) : BaseUseCase<Unit, String> {

    override fun invoke(data: Unit): String {
        return repo.getCountryFlag()
    }
}
