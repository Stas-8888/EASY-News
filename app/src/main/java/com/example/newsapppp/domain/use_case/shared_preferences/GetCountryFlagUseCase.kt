package com.example.newsapppp.domain.use_case.shared_preferences

import com.example.newsapppp.domain.base.BaseUseCase
import com.example.newsapppp.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

/**
 * This use case retrieves the country flag from shared preferences.
 * @param contract The shared preferences repository used to retrieve the country flag.
 */
class GetCountryFlagUseCase @Inject constructor(private val contract: SharedPreferencesRepository) :
    BaseUseCase<Unit, String> {

    /**
     * Invokes the use case and retrieves the country flag from shared preferences.
     * @param data Not used in this use case.
     * @return The country flag as a string.
     */
    override fun invoke(data: Unit) = contract.getCountryFlag()
}
