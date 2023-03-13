package com.example.newsapppp.domain.interactors.sharedpreferences

interface SharedPreferencesContract {

    suspend fun saveFavorite(key: String, value: Boolean)

    suspend fun getFavorite(key: String): Boolean

    fun deleteAllFavorite()

    suspend fun saveCountryFlag(value: String)

    fun getCountryFlag(): String

    suspend fun saveSwitchPosition(value: Boolean)

    fun getSwitchPosition(): Boolean
}
