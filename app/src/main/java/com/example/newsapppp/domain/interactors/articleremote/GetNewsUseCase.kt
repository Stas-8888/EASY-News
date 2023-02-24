package com.example.newsapppp.domain.interactors.articleremote

import androidx.paging.PagingData
import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.repository.ArticleRemoteRepositoryContract
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repo: ArticleRemoteRepositoryContract) :
    BaseUseCaseSuspend<String, Flow<PagingData<ArticleModel>>> {

    override suspend fun invoke(data: String): Flow<PagingData<ArticleModel>> {
        return repo.getNews(category = data)
    }
}
