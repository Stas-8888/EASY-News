package com.example.newsapppp.domain.interactors.sharedpreferences

import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import javax.inject.Inject

/**
 * A use case that saves the selected country flag to the shared preferences.
 * @param contract the repository that provides access to the shared preferences.
 */
class SaveCountryFlagUseCase @Inject constructor(private val contract: SharedPreferencesRepository) :
    BaseUseCaseSuspend<String, Unit> {

    /**
     * Saves the selected country flag to the shared preferences.
     * @param data the country flag to be saved.
     */
    override suspend fun invoke(data: String) = contract.saveCountryFlag(data)
}
