package com.example.newsapppp.domain.use_case.articleremote

import com.example.newsapppp.domain.repository.ArticleRepository
import com.example.newsapppp.domain.use_case.articles.remote.SearchArticlesUseCase
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.model.NewsResponseModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class SearchArticlesUseCaseTest {

    private val mockRepo = mock<ArticleRepository>()

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

        `when`(mockRepo.searchArticles(query)).thenReturn(expectedResult)
        val useCase = SearchArticlesUseCase(mockRepo)
        val result = useCase.invoke(query)
        assertEquals(expectedResult, result)
    }
}
