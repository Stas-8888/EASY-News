package com.example.newsapppp.data.mapper

import com.example.newsapppp.data.db.models.ArticleEntity
import com.example.newsapppp.data.network.model.ArticleRemote
import com.example.newsapppp.data.network.model.NewsResponseRemote
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
}
