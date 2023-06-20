package com.example.newsapppp.data.source.remote.model

data class ArticleRemote(
    val id: Int = 0,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String,
    val urlToImage: String?
)
