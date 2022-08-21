package com.example.newsapppp.domain.usecase

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow

class GetRoomArticleUseCase(private val repo: ArticleRepository) {

    fun getAllArticles(): Flow<List<ArticleModel>> {
        return repo.getAllArticles()
    }
}
