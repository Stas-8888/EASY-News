package com.example.newsapppp.domain.usecase

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.repository.DbRepository

class DeleteArticleUseCase(private val repo: DbRepository) {

    suspend fun delete(article: ArticleModel) {
        repo.delete(article)
    }
}
