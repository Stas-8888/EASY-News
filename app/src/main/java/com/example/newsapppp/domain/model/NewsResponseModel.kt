package com.example.newsapppp.domain.model

data class NewsResponseModel(
    val articlesModel: List<ArticleModel>,
    val status: String,
    val totalResults: Int
)
