package com.example.newsapppp.data.network.model

data class ArticleDto(
    val id: Int = 0,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String,
    val urlToImage: String?
)
