package com.example.newsapppp.domain.model

/**
 * Class that represents a Currency in the domain layer.
 */
data class ArticleModel(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String,
    val urlToImage: String?
)
