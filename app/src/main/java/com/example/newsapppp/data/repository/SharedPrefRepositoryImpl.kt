package com.example.newsapppp.data.repository

import android.content.Context
import android.preference.PreferenceManager
import com.example.newsapppp.domain.repository.SharedPrefRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val SHARED_KEY_COUNTRY = "qww"
private const val SHARED_KEY_SWITCH_POSITION = "yes"
private const val DEFAULT_BOOLEAN = false

@Suppress("DEPRECATION")
class SharedPrefRepositoryImpl @Inject constructor(
    @ApplicationContext var context: Context
) : SharedPrefRepository {

    private val favoriteShared = PreferenceManager.getDefaultSharedPreferences(context)

    override suspend fun saveCountryFlag(value: String) = withContext(Dispatchers.IO) {
        favoriteShared.edit().putString(SHARED_KEY_COUNTRY, value).apply()
    }

    override fun getCountryFlag(): String {
        return favoriteShared.getString(SHARED_KEY_COUNTRY, "us") ?: "us"
    }

    override suspend fun saveFavorite(key: String, value: Boolean) = withContext(Dispatchers.IO) {
        putBoolean(key, value)
    }

    override suspend fun getFavorite(key: String): Boolean {
        return getBoolean(key)
    }

    override suspend fun saveSwitchPosition(value: Boolean) = withContext(Dispatchers.IO) {
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
