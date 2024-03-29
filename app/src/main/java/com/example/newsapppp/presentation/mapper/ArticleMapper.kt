package com.example.newsapppp.presentation.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.example.newsapppp.common.BaseMapper
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.presentation.model.Article
import javax.inject.Inject

/**
 * Mapper class used to : transformationModels.
 */
class ArticleMapper @Inject constructor() : BaseMapper<Article, ArticleModel> {

    override fun mapToModel(data: Article) = ArticleModel(
        author = data.author,
        content = data.content,
        description = data.description,
        publishedAt = data.publishedAt,
        title = data.title,
        url = data.url,
        urlToImage = data.urlToImage
    )

    override fun mapFromModel(data: ArticleModel) = Article(
        author = data.author,
        content = data.content,
        description = data.description,
        publishedAt = data.publishedAt,
        title = data.title,
        url = data.url,
        urlToImage = data.urlToImage
    )

    fun mapToListArticle(article: List<ArticleModel>): List<Article> {
        return article.map { mapFromModel(it) }
    }

    fun mapToPagingArticle(article: PagingData<ArticleModel>): PagingData<Article> {
        return article.map { mapFromModel(it) }
    }
}
