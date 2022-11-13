package com.example.newsapppp.domain.interactors

import com.example.newsapppp.domain.repository.BaseUseCase
import com.example.newsapppp.domain.repository.SharedPrefRepository

class GetSwitchPositionUseCase(private val repo: SharedPrefRepository): BaseUseCase<Unit, Boolean> {

    override fun invoke(data: Unit): Boolean {
        return repo.getSwitchPosition()
    }
}
