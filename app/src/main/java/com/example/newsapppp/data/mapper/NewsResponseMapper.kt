package com.example.newsapppp.data.mapper

import com.example.newsapppp.data.db.ArticleDbModel
import com.example.newsapppp.data.network.model.ArticleDto
import com.example.newsapppp.data.network.model.NewsResponseDto
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.model.NewsResponseModel

class NewsResponseMapper(private val articleMapper: ArticleMapper) {

    fun converterToNewsResponseModel(data: NewsResponseDto): NewsResponseModel {
        return NewsResponseModel(
            articlesModel = articleToModelArticle(data.articles),
            status = data.status,
            totalResults = data.totalResults
        )
    }

    fun articleToModelArticle(article: List<ArticleDto>): List<ArticleModel> {
        return article.map { articleMapper.convertToModel(it) }
    }

    fun articleDbToArticleModel(article: List<ArticleDbModel>): List<ArticleModel> {
        return article.map { articleMapper.mapArticleModelFromArticleDb(it) }
    }
}
