package com.example.newsapppp

import com.example.newsapppp.domain.interactors.GetNewsUseCase
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.model.NewsResponseModel
import com.example.newsapppp.domain.repository.ArticleRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetNewsUseCaseTest{
    private val articleRepository = mock<ArticleRepository>()

    @Test
    fun shouldReturnCorrectData() = runBlocking {
        val getNewsUseCase = GetNewsUseCase(articleRepository)
        val actual = getNewsUseCase.getNews("s","s")
        val expected = articleRepository.getNews("s","s")

        assertEquals(expected, actual)
    }
}



//        val newsResponseModel = NewsResponseModel( articleModel,"s",2)
//        Mockito.`when`(articleRepository.getNews("s","s")).thenReturn(newsResponseModel)

