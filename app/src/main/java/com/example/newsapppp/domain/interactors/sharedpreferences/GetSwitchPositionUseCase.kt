package com.example.newsapppp.domain.interactors.sharedpreferences

import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCase
import javax.inject.Inject

/**
 * A use case that retrieves the switch position from the shared preferences.
 * @param contract the repository that provides access to the shared preferences.
 */
class GetSwitchPositionUseCase @Inject constructor(private val contract: SharedPreferencesRepository) :
    BaseUseCase<Unit, Boolean> {

    /**
     * Retrieves the switch position from the shared preferences.
     * @param data unused parameter.
     * @return the switch position (true if on, false if off).
     */
    override fun invoke(data: Unit) = contract.getSwitchPosition()
}
