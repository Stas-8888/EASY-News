package com.example.newsapppp.domain.usecase

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.repository.DbRepository
import javax.inject.Inject

class InsertArticleUseCase @Inject constructor(private val repo: DbRepository) {

    suspend fun insertArticle(article: ArticleModel) {
        repo.insertArticle(article)
    }
}
