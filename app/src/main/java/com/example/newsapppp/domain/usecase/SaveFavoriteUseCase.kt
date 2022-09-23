package com.example.newsapppp.domain.usecase

import com.example.newsapppp.domain.repository.SharedPrefRepository

class SaveFavoriteUseCase(private val repo: SharedPrefRepository) {

    suspend fun saveFavorite(key: String, value: Boolean) {
        repo.saveFavorite(key, value)
    }
}
