package com.example.newsapppp.domain.usecase

import com.example.newsapppp.domain.repository.SharedPrefRepository

class SaveSwitchPositionUseCase(private val repo: SharedPrefRepository) {

    suspend fun saveSwitchPosition(value: Boolean) {
        repo.saveSwitchPosition(value)
    }
}
