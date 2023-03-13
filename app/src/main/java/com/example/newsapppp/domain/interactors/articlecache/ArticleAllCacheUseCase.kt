package com.example.newsapppp.domain.interactors.articlecache

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleAllCacheUseCase @Inject constructor(private val repo: ArticleCacheContract) :
    BaseUseCase<Unit, Flow<List<ArticleModel>>> {

    override fun invoke(data: Unit): Flow<List<ArticleModel>> {
        return repo.getAllArticles()
    }
}
