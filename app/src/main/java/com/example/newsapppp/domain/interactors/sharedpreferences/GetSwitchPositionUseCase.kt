package com.example.newsapppp.domain.interactors.sharedpreferences

import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCase
import javax.inject.Inject

class GetSwitchPositionUseCase @Inject constructor(private val repo: SharedPreferencesContract) :
    BaseUseCase<Unit, Boolean> {

    override fun invoke(data: Unit): Boolean {
        return repo.getSwitchPosition()
    }
}
