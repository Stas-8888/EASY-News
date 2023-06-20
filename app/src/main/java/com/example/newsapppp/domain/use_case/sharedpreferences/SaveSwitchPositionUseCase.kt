package com.example.newsapppp.domain.use_case.sharedpreferences

import com.example.newsapppp.domain.base_use_case.BaseUseCaseSuspend
import com.example.newsapppp.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

/**
 * This class represents the use case for saving the position of a switch in SharedPreferences.
 *
 * @param contract The repository that handles the SharedPreferences operations.
 */
class SaveSwitchPositionUseCase @Inject constructor(private val contract: SharedPreferencesRepository) :
    BaseUseCaseSuspend<Boolean, Unit> {

    /**
     * Saves the given switch position value in SharedPreferences.
     *
     * @param data The boolean value representing the position of the switch.
     */
    override suspend fun invoke(data: Boolean) = contract.saveSwitchPosition(data)
}