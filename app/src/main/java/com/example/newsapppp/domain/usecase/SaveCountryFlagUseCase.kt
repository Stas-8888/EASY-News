package com.example.newsapppp.domain.usecase

import com.example.newsapppp.domain.repository.SharedPrefRepository

class SaveCountryFlagUseCase(private val repo: SharedPrefRepository) {

    suspend fun saveCountryFlag(value: String) {
        repo.saveCountryFlag(value)
    }
}
