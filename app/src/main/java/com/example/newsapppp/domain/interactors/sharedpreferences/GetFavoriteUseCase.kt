package com.example.newsapppp.domain.interactors.sharedpreferences

import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import javax.inject.Inject

/**
 * This use case retrieves the favorite status of an article from shared preferences.
 * @param contract The shared preferences repository used to retrieve the favorite status.
 */
class GetFavoriteUseCase @Inject constructor(private val contract: SharedPreferencesRepository) :
    BaseUseCaseSuspend<String, Boolean> {

    /**
     * Invokes the use case and retrieves the favorite status of an article from shared preferences.
     * @param data The ID of the article to retrieve the favorite status for.
     * @return True if the article is marked as a favorite, false otherwise.
     */
    override suspend fun invoke(data: String) = contract.getFavorite(data)
}
