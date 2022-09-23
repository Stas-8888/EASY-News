package com.example.newsapppp.data.mapper

import com.example.newsapppp.data.db.models.ArticleDbModel
import com.example.newsapppp.data.network.model.ArticleDto
import com.example.newsapppp.domain.model.ArticleModel

class ArticleMapper {

    fun convertToModel(data: ArticleDto) = ArticleModel(
        author = data.author,
        content = data.content,
        description = data.description,
        publishedAt = data.publishedAt,
        title = data.title,
        url = data.url,
        urlToImage = data.urlToImage
    )

    fun mapArticleModelToArticleDb(data: ArticleModel) = ArticleDbModel(
        author = data.author,
        content = data.content,
        description = data.description,
        publishedAt = data.publishedAt,
        title = data.title,
        url = data.url,
        urlToImage = data.urlToImage
    )

    fun mapArticleModelFromArticleDb(data: ArticleDbModel) = ArticleModel(
        author = data.author,
        content = data.content,
        description = data.description,
        publishedAt = data.publishedAt,
        title = data.title,
        url = data.url,
        urlToImage = data.urlToImage
    )
}
