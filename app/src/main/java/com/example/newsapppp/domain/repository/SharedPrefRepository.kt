package com.example.newsapppp.domain.repository

interface SharedPrefRepository {

    suspend fun saveFavorite(value: Boolean)

    suspend fun getFavorite(): Boolean

    suspend fun saveCountryFlag(value: String)

    fun getCountryFlag(): String

    suspend fun saveSwitchPosition(value: Boolean)

    fun getSwitchPosition(): Boolean
}
