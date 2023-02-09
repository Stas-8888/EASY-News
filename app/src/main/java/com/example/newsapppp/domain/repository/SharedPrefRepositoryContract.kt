package com.example.newsapppp.domain.repository

interface SharedPrefRepositoryContract {

    suspend fun saveFavorite(key: String, value: Boolean)

    suspend fun getFavorite(key: String): Boolean

    fun deleteAllFavorite()

    suspend fun saveCountryFlag(value: String)

    fun getCountryFlag(): String

    suspend fun saveSwitchPosition(value: Boolean)

    fun getSwitchPosition(): Boolean
}
