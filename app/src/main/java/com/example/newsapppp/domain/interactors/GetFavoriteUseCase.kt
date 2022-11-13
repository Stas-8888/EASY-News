package com.example.newsapppp.domain.interactors

import com.example.newsapppp.domain.repository.BaseUseCaseSuspend
import com.example.newsapppp.domain.repository.SharedPrefRepository

class GetFavoriteUseCase(private val repo: SharedPrefRepository) :
    BaseUseCaseSuspend<String, Boolean> {

    override suspend fun invoke(data: String): Boolean {
        return repo.getFavorite(data)
    }
}
