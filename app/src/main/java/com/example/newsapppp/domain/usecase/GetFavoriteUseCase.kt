package com.example.newsapppp.domain.usecase

import com.example.newsapppp.domain.repository.SharedPrefRepository

class GetFavoriteUseCase(private val repo: SharedPrefRepository) {

    suspend fun getFavorite(key: String): Boolean {
        return repo.getFavorite(key)
    }
}
