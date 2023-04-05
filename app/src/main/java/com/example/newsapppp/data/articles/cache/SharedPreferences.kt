package com.example.newsapppp.data.articles.cache

import android.content.Context
import com.example.newsapppp.core.dispatcher.DispatcherRepositoryContract
import com.example.newsapppp.domain.interactors.sharedpreferences.SharedPreferencesContract
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

// Keys used for accessing shared preferences data
private const val SHARED_KEY_COUNTRY = "country"
private const val SHARED_KEY_SWITCH_POSITION = "switch"
private const val DEFAULT_BOOLEAN = false

/**
 * Class that provides methods for accessing shared preferences data.
 * Implements the [SharedPreferencesContract] interface.
 */
class SharedPreferences @Inject constructor(
    @ApplicationContext var context: Context,
    private val dispatcher: DispatcherRepositoryContract
) : SharedPreferencesContract {

    private val favoriteShared = context.getSharedPreferences("shared", Context.MODE_PRIVATE)

    /**
     * Save the value of the selected country flag in shared preferences.
     * Runs on a background thread using the IO dispatcher.
     */
    override suspend fun saveCountryFlag(value: String) = dispatcher.io {
        favoriteShared.edit().putString(SHARED_KEY_COUNTRY, value).apply()
    }

    /**
     * Get the value of the selected country flag from shared preferences.
     * Returns "us" if the value is not found in shared preferences.
     */
    override fun getCountryFlag(): String {
        return favoriteShared.getString(SHARED_KEY_COUNTRY, "us") ?: "us"
    }

    /**
     * Save the value of a favorite item in shared preferences.
     * Runs on a background thread using the IO dispatcher.
     */
    override suspend fun saveFavorite(key: String, value: Boolean) = dispatcher.io {
        putBoolean(key, value)
    }

    /**
     * Get the value of a favorite item from shared preferences.
     * Returns false if the value is not found in shared preferences.
     */
    override suspend fun getFavorite(key: String): Boolean {
        return getBoolean(key)
    }

    /**
     * Delete all items from shared preferences.
     */
    override fun deleteAllFavorite() {
        favoriteShared.edit().clear().apply()
    }

    /**
     * Save the value of the switch position in shared preferences.
     * Runs on a background thread using the IO dispatcher.
     */
    override suspend fun saveSwitchPosition(value: Boolean) = dispatcher.io {
        putBoolean(SHARED_KEY_SWITCH_POSITION, value)
    }

    /**
     * Get the value of the switch position from shared preferences.
     * Returns false if the value is not found in shared preferences.
     */
    override fun getSwitchPosition(): Boolean {
        return getBoolean(SHARED_KEY_SWITCH_POSITION)
    }

    /**
     * Get a boolean value from shared preferences.
     * Returns the default value if the key is not found in shared preferences.
     */
    private fun getBoolean(key: String): Boolean {
        return favoriteShared.getBoolean(key, DEFAULT_BOOLEAN)
    }

    /**
     * Save a boolean value to shared preferences.
     */
    private fun putBoolean(key: String, value: Boolean) {
        favoriteShared.edit().putBoolean(key, value).apply()
    }
}
