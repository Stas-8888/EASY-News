package com.example.newsapppp.domain.interactors.firebase

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
