package com.example.newsapppp.domain.interactors.articleremote

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.model.NewsResponseModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class SearchNewsUseCaseTest {

    private val mockRepo = mock<ArticleRemoteContract>()

    @Test
    fun `invoke should return expected result`() = runBlocking {
        val query = "example"
        val expectedArticles = listOf(
            ArticleModel(
                "John Doe",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                "Lorem ipsum",
                "2022-01-01T00:00:00Z",
                "Example Title",
                "https://example.com",
                "https://example.com/image.jpg"
            )
        )

        val expectedResult = NewsResponseModel(expectedArticles, "ok", 1)

        `when`(mockRepo.searchNews(query)).thenReturn(expectedResult)
        val useCase = SearchNewsUseCase(mockRepo)
        val result = useCase.invoke(query)
        assertEquals(expectedResult, result)
    }
}
