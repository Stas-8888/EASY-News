package com.example.newsapppp.domain.interactors.localsource

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCase
import com.example.newsapppp.domain.repository.ArticleLocalSourceRepositoryContract
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalArticleUseCase @Inject constructor(private val repo: ArticleLocalSourceRepositoryContract) :
    BaseUseCase<Unit, Flow<List<ArticleModel>>> {

    override fun invoke(data: Unit): Flow<List<ArticleModel>> {
        return repo.getAllArticles()
    }
}
