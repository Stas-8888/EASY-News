package com.example.newsapppp.domain.interactors.room

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.repository.BaseUseCase
import com.example.newsapppp.domain.repository.DataBaseRepositoryContract
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRoomArticleUseCase @Inject constructor(private val repo: DataBaseRepositoryContract) :
    BaseUseCase<Unit, Flow<List<ArticleModel>>> {

    override fun invoke(data: Unit): Flow<List<ArticleModel>> {
        return repo.getAllArticles()
    }
}
