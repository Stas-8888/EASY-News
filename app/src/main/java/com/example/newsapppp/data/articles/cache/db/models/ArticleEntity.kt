package com.example.newsapppp.data.articles.cache.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapppp.data.articles.cache.db.dao.ArticleDao.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class ArticleEntity(
    @PrimaryKey @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "publishedAt") val publishedAt: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "urlToImage") val urlToImage: String?,
)
