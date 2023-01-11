package com.example.newsapppp.domain.interactors.retrofit

import com.example.newsapppp.domain.model.NewsResponseModel
import com.example.newsapppp.domain.repository.ArticleRepository

class GetNewsUseCase(private val repo: ArticleRepository) {

    suspend fun getNews(category: String): NewsResponseModel {
        return repo.getNews(category = category)
    }
}
