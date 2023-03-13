package com.example.newsapppp.domain.interactors.sharedpreferences

import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCase
import javax.inject.Inject

class GetCountryFlagUseCase @Inject constructor(private val repo: SharedPreferencesContract) :
    BaseUseCase<Unit, String> {

    override fun invoke(data: Unit): String {
        return repo.getCountryFlag()
    }
}
