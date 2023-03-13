package com.example.newsapppp.domain.interactors.sharedpreferences

import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(private val repo: SharedPreferencesContract) :
    BaseUseCaseSuspend<String, Boolean> {

    override suspend fun invoke(data: String): Boolean {
        return repo.getFavorite(data)
    }
}
