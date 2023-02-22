package com.example.newsapppp.domain.interactors.articleremote

import com.example.newsapppp.domain.model.NewsResponseModel
import com.example.newsapppp.domain.repository.ArticleRepositoryContract
import com.example.newsapppp.domain.interactors.baseusecase.BaseUseCaseSuspend
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(private val repo: ArticleRepositoryContract) :
    BaseUseCaseSuspend<String, NewsResponseModel> {

    override suspend fun invoke(data: String): NewsResponseModel {
        return repo.searchNews(data)
    }
}