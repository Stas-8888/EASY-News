package com.example.newsapppp.domain.usecase

import com.example.newsapppp.domain.model.NewsResponseModel
import com.example.newsapppp.domain.repository.ArticleRepository

class SearchNewsUseCase(private val repo: ArticleRepository) {

    suspend fun searchNews(searchQuery: String): NewsResponseModel {
        return repo.searchNews(searchQuery)
    }
}
