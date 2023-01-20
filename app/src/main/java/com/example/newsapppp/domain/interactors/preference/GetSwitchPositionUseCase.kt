package com.example.newsapppp.domain.interactors.preference

import com.example.newsapppp.domain.repository.BaseUseCase
import com.example.newsapppp.domain.repository.SharedPrefRepositoryContract

class GetSwitchPositionUseCase(private val repo: SharedPrefRepositoryContract): BaseUseCase<Unit, Boolean> {

    override fun invoke(data: Unit): Boolean {
        return repo.getSwitchPosition()
    }
}
