package com.example.newsapppp.presentation.mapper

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.presentation.model.Article

internal fun Article.toArticleModel(): ArticleModel {
    return ArticleModel(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

internal fun Article.toArticle(): Article {
    return Article(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}
