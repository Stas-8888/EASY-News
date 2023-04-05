package com.example.newsapppp.data.articles.mapper

import com.example.newsapppp.core.BaseMapper
import com.example.newsapppp.data.articles.cache.db.models.ArticleEntity
import com.example.newsapppp.data.articles.remote.model.ArticleRemote
import com.example.newsapppp.domain.model.ArticleModel
import javax.inject.Inject

class EntityMapper @Inject constructor() : BaseMapper<ArticleModel, ArticleEntity> {

    fun convertToModel(data: ArticleRemote) = ArticleModel(
        author = data.author,
        content = data.content,
        description = data.description,
        publishedAt = data.publishedAt,
        title = data.title,
        url = data.url,
        urlToImage = data.urlToImage
    )

    override fun mapToModel(data: ArticleModel) = ArticleEntity(
        author = data.author,
        content = data.content,
        description = data.description,
        publishedAt = data.publishedAt,
        title = data.title,
        url = data.url,
        urlToImage = data.urlToImage
    )

    override fun mapFromModel(data: ArticleEntity) = ArticleModel(
        author = data.author,
        content = data.content,
        description = data.description,
        publishedAt = data.publishedAt,
        title = data.title,
        url = data.url,
        urlToImage = data.urlToImage
    )
}
