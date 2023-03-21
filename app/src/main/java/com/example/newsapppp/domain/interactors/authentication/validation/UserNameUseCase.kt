package com.example.newsapppp.domain.interactors.authentication.validation

import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCase
import javax.inject.Inject

/**
 * This use case validates a user name.
 * @param repository The validation repository used to validate the user name.
 */
class UserNameUseCase @Inject constructor(val repository: ValidationRepositoryContract) :
    BaseUseCase<String, String> {

    /**
     * Invokes the use case and validates the specified user name.
     * @param data The user name to validate.
     * @return The validated user name.
     */
    override fun invoke(data: String): String {
        return repository.userName(data)
    }
}
