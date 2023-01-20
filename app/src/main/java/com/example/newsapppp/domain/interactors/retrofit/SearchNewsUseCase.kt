package com.example.newsapppp.domain.interactors.retrofit

import com.example.newsapppp.domain.model.NewsResponseModel
import com.example.newsapppp.domain.repository.ArticleRepositoryContract
import com.example.newsapppp.domain.repository.BaseUseCaseSuspend

class SearchNewsUseCase(private val repo: ArticleRepositoryContract) :
    BaseUseCaseSuspend<String, NewsResponseModel> {

    override suspend fun invoke(data: String): NewsResponseModel {
        return repo.searchNews(data)
    }
}
