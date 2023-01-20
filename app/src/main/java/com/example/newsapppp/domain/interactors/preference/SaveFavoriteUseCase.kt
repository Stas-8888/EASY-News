package com.example.newsapppp.domain.interactors.preference

import com.example.newsapppp.domain.repository.SharedPrefRepositoryContract

class SaveFavoriteUseCase(private val repo: SharedPrefRepositoryContract) {

    suspend fun saveFavorite(key: String, value: Boolean) {
        repo.saveFavorite(key, value)
    }
}
