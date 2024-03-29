package com.example.newsapppp.data.mapper

import com.example.newsapppp.data.source.cache.model.ArticleEntity
import com.example.newsapppp.data.source.remote.model.ArticleRemote
import com.example.newsapppp.data.source.remote.model.NewsResponseRemote
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.model.NewsResponseModel
import javax.inject.Inject

class NewsResponseMapper @Inject constructor(private val entityMapper: EntityMapper) {

    fun converterToNewsResponseModel(data: NewsResponseRemote): NewsResponseModel {
        return NewsResponseModel(
            articlesModel = fromArticleRemoteToArticleModel(data.articles),
            status = data.status,
            totalResults = data.totalResults
        )
    }

    private fun fromArticleRemoteToArticleModel(article: List<ArticleRemote>): List<ArticleModel> {
        return article.map { entityMapper.convertToModel(it) }
    }

    fun fromArticleEntityToArticleModel(article: List<ArticleEntity>): List<ArticleModel> {
        return article.map { entityMapper.mapFromModel(it) }
    }

    private fun mapFromRemote(data: ArticleRemote) = ArticleModel(
        author = data.author,
        content = data.content,
        description = data.description,
        publishedAt = data.publishedAt,
        title = data.title,
        url = data.url,
        urlToImage = data.urlToImage
    )

    fun mapToListArticleRemote(article: List<ArticleRemote>): List<ArticleModel> {
        return article.map { mapFromRemote(it) }
    }
}
