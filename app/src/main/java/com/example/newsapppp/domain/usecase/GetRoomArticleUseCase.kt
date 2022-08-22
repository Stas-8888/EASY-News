package com.example.newsapppp.domain.usecase

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.repository.DbRepository
import kotlinx.coroutines.flow.Flow

class GetRoomArticleUseCase(private val repo: DbRepository) {

    fun getAllArticles(): Flow<List<ArticleModel>> {
        return repo.getAllArticles()
    }
}
