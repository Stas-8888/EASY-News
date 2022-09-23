package com.example.newsapppp.domain.repository

interface SharedPrefRepository {

    suspend fun saveFavorite(key: String, value: Boolean)

    suspend fun getFavorite(key: String): Boolean

    suspend fun saveCountryFlag(value: String)

    fun getCountryFlag(): String

    suspend fun saveSwitchPosition(value: Boolean)

    fun getSwitchPosition(): Boolean
}
