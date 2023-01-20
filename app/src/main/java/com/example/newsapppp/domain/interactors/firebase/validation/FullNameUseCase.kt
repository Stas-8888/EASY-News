package com.example.newsapppp.domain.interactors.firebase.validation

import com.example.newsapppp.domain.repository.BaseUseCase
import javax.inject.Inject

class FullNameUseCase @Inject constructor(val repository: ValidationRepositoryContract) :
    BaseUseCase<String, String> {

    override fun invoke(data: String): String {
        return repository.fullName(data)
    }
}
