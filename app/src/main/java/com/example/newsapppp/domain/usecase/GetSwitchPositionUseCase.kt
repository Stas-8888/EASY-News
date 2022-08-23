package com.example.newsapppp.domain.usecase

import com.example.newsapppp.domain.repository.SharedPrefRepository

class GetSwitchPositionUseCase(private val repo: SharedPrefRepository) {

    fun getSwitchPosition(): Boolean {
        return repo.getSwitchPosition()
    }
}
