package com.example.newsapppp.domain.interactors.preference

import com.example.newsapppp.domain.repository.BaseUseCaseSuspend
import com.example.newsapppp.domain.repository.SharedPrefRepositoryContract
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(private val repo: SharedPrefRepositoryContract) :
    BaseUseCaseSuspend<String, Boolean> {

    override suspend fun invoke(data: String): Boolean {
        return repo.getFavorite(data)
    }
}
