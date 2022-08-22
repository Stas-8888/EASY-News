package com.example.newsapppp.data.repository

import android.content.Context
import android.preference.PreferenceManager
import com.example.newsapppp.domain.repository.SharedPrefRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val SHARED_KEY = "qw"
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
}