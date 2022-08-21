package com.example.newsapppp.domain.usecase

import com.example.newsapppp.domain.repository.ArticleRepository

class DeleteAllUseCase(private val repo: ArticleRepository) {

    suspend fun deleteAll() {
        repo.deleteAll()
    }
}
