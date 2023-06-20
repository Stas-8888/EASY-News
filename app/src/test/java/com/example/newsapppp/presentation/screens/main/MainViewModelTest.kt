package com.example.newsapppp.presentation.screens.main

import com.example.newsapppp.common.network.NetworkHandlerRepository
import com.example.newsapppp.data.interceptor.ErrorsInterceptorContract
import com.example.newsapppp.domain.use_case.articles.remote.FetchedArticlesUseCase
import com.example.newsapppp.domain.use_case.sharedpreferences.GetCountryFlagUseCase
import com.example.newsapppp.presentation.mapper.ArticleMapper
import com.example.newsapppp.presentation.model.Article
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class MainViewModelTest{

    private val mapper = mock<ArticleMapper>()
    private val getCountryFlag = mock<GetCountryFlagUseCase>()
    private val fetchedArticles = mock<FetchedArticlesUseCase>()
    private val interceptorErrors = mock<ErrorsInterceptorContract>()
    private val networkHandlerRepository = mock<NetworkHandlerRepository>()

    private lateinit var viewModel: MainViewModel

    val mockArticle = Article(
        title = "Mock article title",
        description = "Mock article description",
        url = "http://example.com",
        urlToImage = "http://example.com/image.jpg",
        publishedAt = "2023-03-08T12:00:00Z",
        content = "Mock article content",
        author = "Mock author"
    )

    private val categories = listOf(
        "Technology", "Sports", "Science", "Entertainment", "Business", "Health"
    )

    @Before
    fun setUp() {
        viewModel = MainViewModel(mapper, getCountryFlag, fetchedArticles, interceptorErrors)
    }

    @Test
    fun `fetchAndShowArticles when offline, should emit ShowNetworkDialog action`() = runBlocking {

    }

}