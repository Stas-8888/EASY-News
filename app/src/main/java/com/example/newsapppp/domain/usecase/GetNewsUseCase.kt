package com.example.newsapppp.domain.usecase

import com.example.newsapppp.domain.model.NewsResponseModel
import com.example.newsapppp.domain.repository.ArticleRepository

class GetNewsUseCase(private val repo: ArticleRepository) {

    suspend fun getNews(countryCode: String, category: String): NewsResponseModel {
        return repo.getNews(countryCode = countryCode, category = category)
    }
}
