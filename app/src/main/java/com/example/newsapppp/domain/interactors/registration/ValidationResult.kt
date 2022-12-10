package com.example.newsapppp.domain.interactors.registration

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
