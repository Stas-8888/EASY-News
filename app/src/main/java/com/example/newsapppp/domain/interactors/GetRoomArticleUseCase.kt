package com.example.newsapppp.domain.interactors

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.repository.BaseUseCase
import com.example.newsapppp.domain.repository.DbRepository
import kotlinx.coroutines.flow.Flow

class GetRoomArticleUseCase(private val repo: DbRepository) :
    BaseUseCase<Unit, Flow<List<ArticleModel>>> {

    override fun invoke(data: Unit): Flow<List<ArticleModel>> {
        return repo.getAllArticles()
    }
}
