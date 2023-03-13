package com.example.newsapppp.domain.interactors.sharedpreferences

import javax.inject.Inject

class SaveFavoriteUseCase @Inject constructor(private val repo: SharedPreferencesContract) {

    suspend fun saveFavorite(key: String, value: Boolean) {
        repo.saveFavorite(key, value)
    }
}
