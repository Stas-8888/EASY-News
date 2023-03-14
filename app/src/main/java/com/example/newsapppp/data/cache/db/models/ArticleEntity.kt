package com.example.newsapppp.data.cache.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapppp.data.cache.db.dao.ArticleDao.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class ArticleEntity(
    @PrimaryKey
    val url: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val urlToImage: String?,
)
