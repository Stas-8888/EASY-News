package com.example.newsapppp.domain.interactors.articleRemote

import androidx.paging.PagingData
import com.example.newsapppp.domain.interactors.baseUseCase.BaseUseCaseSuspend
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.repository.ArticleRepositoryContract
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repo: ArticleRepositoryContract) :
    BaseUseCaseSuspend<String, Flow<PagingData<ArticleModel>>> {

    override suspend fun invoke(data: String): Flow<PagingData<ArticleModel>> {
        return repo.getNews(category = data)
    }
}
