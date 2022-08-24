package com.example.newsapppp.domain.usecase

import com.example.newsapppp.domain.repository.DbRepository

class DeleteAllUseCase(private val repo: DbRepository) {

    suspend fun deleteAllArticle() {
        repo.deleteAllArticle()
    }
}
