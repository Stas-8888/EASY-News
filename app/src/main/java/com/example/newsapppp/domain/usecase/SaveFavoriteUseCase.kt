package com.example.newsapppp.domain.usecase

import com.example.newsapppp.domain.repository.SharedPrefRepository

class SaveFavoriteUseCase(private val repo: SharedPrefRepository) {

    suspend fun saveFavorite(value: Boolean) {
        repo.saveFavorite(value)
    }
}
