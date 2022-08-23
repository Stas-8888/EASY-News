package com.example.newsapppp.domain.usecase

import com.example.newsapppp.domain.repository.SharedPrefRepository

class GetCountryFlagUseCase(private val repo: SharedPrefRepository) {

     fun getCountryFlag(): String {
        return repo.getCountryFlag()
    }
}
