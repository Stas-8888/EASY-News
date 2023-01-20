package com.example.newsapppp

import com.example.newsapppp.domain.interactors.retrofit.GetNewsUseCase
import com.example.newsapppp.domain.repository.ArticleRepositoryContract
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock

class GetNewsUseCaseTest{
    private val articleRepositoryContract = mock<ArticleRepositoryContract>()

    @Test
    fun shouldReturnCorrectData() = runBlocking {
        val getNewsUseCase = GetNewsUseCase(articleRepositoryContract)
        val actual = getNewsUseCase.getNews("s","s")
        val expected = articleRepositoryContract.getNews("s","s")

        assertEquals(expected, actual)
    }
}



//        val newsResponseModel = NewsResponseModel( articleModel,"s",2)
//        Mockito.`when`(articleRepository.getNews("s","s")).thenReturn(newsResponseModel)

