package com.example.newsapppp.domain.usecase

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.repository.ArticleRepository

class DeleteArticleUseCase(private val repo: ArticleRepository) {

    suspend fun delete(article: ArticleModel) {
        repo.delete(article)
    }
}
