package com.example.newsapppp.data.mapper

import com.example.newsapppp.data.db.models.ArticleDbModel
import com.example.newsapppp.data.network.model.ArticleRemote
import com.example.newsapppp.data.network.model.NewsResponseRemote
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.model.NewsResponseModel

class NewsResponseMapper(private val articleMapper: ArticleMapper) {

    fun converterToNewsResponseModel(data: NewsResponseRemote): NewsResponseModel {
        return NewsResponseModel(
            articlesModel = articleToModelArticle(data.articles),
            status = data.status,
            totalResults = data.totalResults
        )
    }

    private fun articleToModelArticle(article: List<ArticleRemote>): List<ArticleModel> {
        return article.map { articleMapper.convertToModel(it) }
    }

    fun articleDbToArticleModel(article: List<ArticleDbModel>): List<ArticleModel> {
        return article.map { articleMapper.mapToEntity(it) }
    }
}
