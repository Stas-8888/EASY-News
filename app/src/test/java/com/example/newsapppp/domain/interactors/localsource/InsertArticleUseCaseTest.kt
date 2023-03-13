package com.example.newsapppp.domain.interactors.localsource

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.repository.ArticleLocalSourceRepositoryContract
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock


class InsertArticleUseCaseTest {

    private val mockRepo = mock<ArticleLocalSourceRepositoryContract>()

    @Test
    fun `inserted article should be returned in getAllArticles`() = runBlocking {
        val mockArticleModel = ArticleModel(
            title = "Mock article title",
            description = "Mock article description",
            url = "http://example.com",
            urlToImage = "http://example.com/image.jpg",
            publishedAt = "2023-03-08T12:00:00Z",
            content = "Mock article content",
            author = "Mock author"
        )

        // Mock the repository to return a flow of a list with the mock article
        Mockito.`when`(mockRepo.getAllArticles()).thenReturn(flowOf(listOf(mockArticleModel)))

        // Call the use case to insert the mock article
        val insertArticleUseCase = InsertArticleUseCase(mockRepo)
        insertArticleUseCase.invoke(mockArticleModel)

        // Verify that the mock article is returned in getAllArticles
        val expected = listOf(mockArticleModel)
        val actual = mockRepo.getAllArticles().first()

        assertEquals(expected, actual)
    }
}
