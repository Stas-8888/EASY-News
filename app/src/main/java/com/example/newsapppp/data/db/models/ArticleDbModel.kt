package com.example.newsapppp.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleDbModel(
    @PrimaryKey
    val url: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val urlToImage: String?,
)
