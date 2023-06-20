package com.example.newsapppp.domain.interactors.sharedpreferences

import javax.inject.Inject

/**
 * This class represents the use case for saving a favorite value in SharedPreferences.
 * @param contract The repository that handles the SharedPreferences operations.
 */
class SaveFavoriteUseCase @Inject constructor(private val contract: SharedPreferencesRepository) {

    /**
     * Saves the given favorite value with the specified key in SharedPreferences.
     * @param key The key used to identify the favorite value.
     * @param value The boolean value representing whether the item is a favorite or not.
     */
    suspend fun invoke(key: String, value: Boolean) = contract.saveFavorite(key, value)
}
