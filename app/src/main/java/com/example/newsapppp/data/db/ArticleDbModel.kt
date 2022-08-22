package com.example.newsapppp.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val author: String?,
    @ColumnInfo
    val content: String?,
    @ColumnInfo
    val description: String?,
    @ColumnInfo
    val publishedAt: String?,
    @ColumnInfo
    val title: String?,
    @ColumnInfo
    val url: String?,
    @ColumnInfo
    val urlToImage: String?
)
