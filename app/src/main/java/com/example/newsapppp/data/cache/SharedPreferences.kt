package com.example.newsapppp.data.cache

import android.content.Context
import com.example.newsapppp.core.dispatcher.DispatcherRepositoryContract
import com.example.newsapppp.domain.interactors.sharedpreferences.SharedPreferencesContract
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val SHARED_KEY_COUNTRY = "country"
private const val SHARED_KEY_SWITCH_POSITION = "switch"
private const val DEFAULT_BOOLEAN = false

class SharedPreferences @Inject constructor(
    @ApplicationContext var context: Context,
    private val dispatcher: DispatcherRepositoryContract
) : SharedPreferencesContract {

    private val favoriteShared = context.getSharedPreferences("shared", Context.MODE_PRIVATE)

    override suspend fun saveCountryFlag(value: String) = dispatcher.io {
        favoriteShared.edit().putString(SHARED_KEY_COUNTRY, value).apply()
    }

    override fun getCountryFlag(): String {
        return favoriteShared.getString(SHARED_KEY_COUNTRY, "us") ?: "us"
    }

    override suspend fun saveFavorite(key: String, value: Boolean) = dispatcher.io {
        putBoolean(key, value)
    }

    override suspend fun getFavorite(key: String): Boolean {
        return getBoolean(key)
    }

    override fun deleteAllFavorite() {
        favoriteShared.edit().clear().apply()
    }

    override suspend fun saveSwitchPosition(value: Boolean) = dispatcher.io {
        putBoolean(SHARED_KEY_SWITCH_POSITION, value)
    }

    override fun getSwitchPosition(): Boolean {
        return getBoolean(SHARED_KEY_SWITCH_POSITION)
    }

    private fun getBoolean(key: String): Boolean {
        return favoriteShared.getBoolean(key, DEFAULT_BOOLEAN)
    }

    private fun putBoolean(key: String, value: Boolean) {
        favoriteShared.edit().putBoolean(key, value).apply()
    }
}
