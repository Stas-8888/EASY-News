package com.example.newsapppp.domain.interactors.registration

import com.example.newsapppp.domain.repository.BaseUseCase
import com.example.newsapppp.domain.repository.RegistrationRepository

class ValidatePasswordUseCase(val repo: RegistrationRepository) : BaseUseCase<String, String> {

    override fun invoke(data: String): String {
        return repo.validatePassword(data)
    }
}
