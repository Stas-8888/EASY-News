package com.example.newsapppp.domain.repository

interface SharedPrefRepository {

    suspend fun saveFavorite(value: Boolean)

    fun getFavorite(): Boolean

}