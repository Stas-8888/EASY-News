package com.example.newsapppp.data.mapper

import com.example.newsapppp.core.BaseMapper
import com.example.newsapppp.data.db.models.ArticleDbModel
import com.example.newsapppp.data.network.model.ArticleRemote
import com.example.newsapppp.domain.model.ArticleModel

class ArticleMapper : BaseMapper<ArticleModel, ArticleDbModel> {

    fun convertToModel(data: ArticleRemote) = ArticleModel(
        author = data.author,
        content = data.content,
        description = data.description,
        publishedAt = data.publishedAt,
        title = data.title,
        url = data.url,
        urlToImage = data.urlToImage
    )

    override fun mapFromEntity(data: ArticleModel) = ArticleDbModel(
        author = data.author,
        content = data.content,
        description = data.description,
        publishedAt = data.publishedAt,
        title = data.title,
        url = data.url,
        urlToImage = data.urlToImage
    )

    override fun mapToEntity(data: ArticleDbModel) = ArticleModel(
        author = data.author,
        content = data.content,
        description = data.description,
        publishedAt = data.publishedAt,
        title = data.title,
        url = data.url,
        urlToImage = data.urlToImage
    )
}
