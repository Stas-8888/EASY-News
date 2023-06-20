package com.example.newsapppp.data.source.remote.model

data class NewsResponseRemote(
    val articles: List<ArticleRemote>,
    val status: String,
    val totalResults: Int
)
