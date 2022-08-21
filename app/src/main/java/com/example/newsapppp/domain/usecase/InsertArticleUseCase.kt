package com.example.newsapppp.domain.usecase

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.repository.ArticleRepository
import javax.inject.Inject

class InsertArticleUseCase @Inject constructor(private val repo: ArticleRepository) {

    suspend fun insert(article: ArticleModel) {
        repo.insert(article)
    }
}
