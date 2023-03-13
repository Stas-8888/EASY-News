package com.example.newsapppp.domain.interactors.articleremote

import androidx.paging.PagingData
import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import com.example.newsapppp.domain.model.ArticleModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repo: ArticleRemoteContract) :
    BaseUseCaseSuspend<String, Flow<PagingData<ArticleModel>>> {

    override suspend fun invoke(data: String): Flow<PagingData<ArticleModel>> {
        return repo.getNews(category = data)
    }
}
