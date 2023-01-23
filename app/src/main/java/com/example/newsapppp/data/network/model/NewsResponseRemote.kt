package com.example.newsapppp.data.network.model

data class NewsResponseRemote(
    val articles: List<ArticleRemote>,
    val status: String,
    val totalResults: Int
)
