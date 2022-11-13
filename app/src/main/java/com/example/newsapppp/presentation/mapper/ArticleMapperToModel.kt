package com.example.newsapppp.presentation.mapper

import com.example.newsapppp.core.EntityMapper
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.presentation.model.Article

/**
 * Mapper class used to : transformationModels.
 */
class ArticleMapperToModel : EntityMapper<Article, ArticleModel> {

    override fun mapFromEntity(data: Article) = ArticleModel(
        author = data.author,
        content = data.content,
        description = data.description,
        publishedAt = data.publishedAt,
        title = data.title,
        url = data.url,
        urlToImage = data.urlToImage
    )

    override fun mapToEntity(data: ArticleModel) = Article(
        author = data.author,
        content = data.content,
        description = data.description,
        publishedAt = data.publishedAt,
        title = data.title,
        url = data.url,
        urlToImage = data.urlToImage
    )

    fun articleToModelArticle(article: List<ArticleModel>): List<Article> {
        return article.map { mapToEntity(it) }
    }
}
