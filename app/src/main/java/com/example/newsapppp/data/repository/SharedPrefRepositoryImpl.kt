package com.example.newsapppp.data.repository

import android.content.Context
import android.preference.PreferenceManager
import com.example.newsapppp.domain.repository.SharedPrefRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val SHARED_KEY = "qw"
private const val SHARED_KEY_COUNTRY = "qww"
private const val SHARED_KEY_SWITCH_POSITION = "yes"
private const val DEFAULT_BOOLEAN = false

@Suppress("DEPRECATION")
class SharedPrefRepositoryImpl @Inject constructor(
    @ApplicationContext var context: Context
) : SharedPrefRepository {

    private val favoriteShared = PreferenceManager.getDefaultSharedPreferences(context)

    override suspend fun saveFavorite(value: Boolean) = withContext(Dispatchers.IO) {
        favoriteShared.edit().putBoolean(SHARED_KEY, value).apply()
    }

    override fun getFavorite(): Boolean {
        return favoriteShared.getBoolean(SHARED_KEY, DEFAULT_BOOLEAN)
    }

    override suspend fun saveCountryFlag(value: String) = withContext(Dispatchers.IO) {
        favoriteShared.edit().putString(SHARED_KEY_COUNTRY, value).apply()
    }

    override fun getCountryFlag(): String {
        return favoriteShared.getString(SHARED_KEY_COUNTRY, "") ?: ""
    }

    override suspend fun saveSwitchPosition(value: Boolean) = withContext(Dispatchers.IO) {
        favoriteShared.edit().putBoolean(SHARED_KEY_SWITCH_POSITION, value).apply()
    }

    override fun getSwitchPosition(): Boolean {
        return favoriteShared.getBoolean(SHARED_KEY_SWITCH_POSITION, DEFAULT_BOOLEAN)
    }
}
