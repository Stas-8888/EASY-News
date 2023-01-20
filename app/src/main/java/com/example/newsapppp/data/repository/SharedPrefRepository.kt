package com.example.newsapppp.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.newsapppp.core.DispatchersList
import com.example.newsapppp.domain.repository.SharedPrefRepositoryContract
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val SHARED_KEY_COUNTRY = "country"
private const val SHARED_KEY_SWITCH_POSITION = "switch"
private const val DEFAULT_BOOLEAN = false

class SharedPrefRepository @Inject constructor(
    @ApplicationContext var context: Context,
    private val dispatchers: DispatchersList
) : SharedPrefRepositoryContract {

    private val favoriteShared = context.getSharedPreferences("shared", MODE_PRIVATE)

    override suspend fun saveCountryFlag(value: String) = dispatchers.io {
        favoriteShared.edit().putString(SHARED_KEY_COUNTRY, value).apply()
    }

    override fun getCountryFlag(): String {
        return favoriteShared.getString(SHARED_KEY_COUNTRY, "us") ?: "us"
    }

    override suspend fun saveFavorite(key: String, value: Boolean) = dispatchers.io {
        putBoolean(key, value)
    }

    override suspend fun getFavorite(key: String): Boolean {
        return getBoolean(key)
    }

    override suspend fun saveSwitchPosition(value: Boolean) = dispatchers.io {
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
