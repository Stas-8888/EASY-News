package com.example.newsapppp.presentation.mapper

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.presentation.model.Article

class ArticleMapperToModel {

    fun convertToModel(data: Article) = ArticleModel(
        id = data.id,
        author = data.author,
        content = data.content,
        description = data.description,
        publishedAt = data.publishedAt,
        title = data.title,
        url = data.url,
        urlToImage = data.urlToImage
    )

    private fun convertFromModel(data: ArticleModel) = Article(
        id = data.id,
        author = data.author,
        content = data.content,
        description = data.description,
        publishedAt = data.publishedAt,
        title = data.title,
        url = data.url,
        urlToImage = data.urlToImage
    )

    fun articleToModelArticle(article: List<ArticleModel>): List<Article> {
        return article.map { convertFromModel(it) }
    }
}
