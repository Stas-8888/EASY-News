package com.example.newsapppp.domain.usecase

import com.example.newsapppp.domain.repository.SharedPrefRepository

class GetFavoriteUseCase(private val repo: SharedPrefRepository) {

     fun getFavorite(): Boolean {
        return repo.getFavorite()
    }
}
