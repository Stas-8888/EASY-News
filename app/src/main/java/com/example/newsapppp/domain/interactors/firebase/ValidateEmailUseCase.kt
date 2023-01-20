package com.example.newsapppp.domain.interactors.firebase

import com.example.newsapppp.domain.repository.BaseUseCase
import com.example.newsapppp.domain.repository.FirebaseRepositoryContract

class ValidateEmailUseCase(val repo: FirebaseRepositoryContract) : BaseUseCase<String, String> {

    override fun invoke(data: String): String {
        return repo.validateEmail(data)
    }
}
