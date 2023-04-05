package com.example.newsapppp.domain.interactors.articleremote

import androidx.paging.PagingData
import com.example.newsapppp.domain.interactors.articles.remote.ArticleRemoteContract
import com.example.newsapppp.domain.interactors.articles.remote.FetchedArticlesUseCase
import com.example.newsapppp.domain.model.ArticleModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class FetchedArticlesUseCaseTest {

    private val mockRepo = mock<ArticleRemoteContract>()

    @Test
    fun `invoke should return flow of PagingData ArticleModel from repository`() = runBlocking {

        val categoryName = "business"
        val mockArticleModel = ArticleModel(
            title = "Mock article title",
            description = "Mock article description",
            url = "http://example.com",
            urlToImage = "http://example.com/image.jpg",
            publishedAt = "2023-03-08T12:00:00Z",
            content = "Mock article content",
            author = "Mock author"
        )
        val pagingData = PagingData.from(listOf(mockArticleModel))
        val pagingSource = flowOf(pagingData)

        whenever(mockRepo.fetchedArticles(categoryName)).thenReturn(pagingSource)

        val fetchedArticlesUseCase = FetchedArticlesUseCase(mockRepo)
        val result = fetchedArticlesUseCase(categoryName).first()
        assertEquals(pagingData, result)
    }

    @Test
    fun `invoke should return multiple pages of PagingData when repository returns multiple pages`() =
        runBlocking {
            val categoryName = "business"
            val mockArticleModel = ArticleModel(
                title = "Mock article title",
                description = "Mock article description",
                url = "http://example.com",
                urlToImage = "http://example.com/image.jpg",
                publishedAt = "2023-03-08T12:00:00Z",
                content = "Mock article content",
                author = "Mock author"
            )
            val pagingData1 = PagingData.from(listOf(mockArticleModel))
            val pagingData2 = PagingData.from(listOf(mockArticleModel, mockArticleModel))
            val pagingData3 =
                PagingData.from(listOf(mockArticleModel, mockArticleModel, mockArticleModel))

            val pagingSource = flowOf(pagingData1, pagingData2, pagingData3)

            whenever(mockRepo.fetchedArticles(categoryName)).thenReturn(pagingSource)

            val fetchedArticlesUseCase = FetchedArticlesUseCase(mockRepo)
            val result = fetchedArticlesUseCase(categoryName).toList()

            assertEquals(3, result.size)
            assertEquals(pagingData1, result[0])
            assertEquals(pagingData2, result[1])
            assertEquals(pagingData3, result[2])
        }

    @Test
    fun `invoke should return empty PagingData when repository returns no articles`() =
        runBlocking {
            val categoryName = "business"
            val pagingData = PagingData.empty<ArticleModel>()

            val pagingSource = flowOf(pagingData)

            whenever(mockRepo.fetchedArticles(categoryName)).thenReturn(pagingSource)

            val fetchedArticlesUseCase = FetchedArticlesUseCase(mockRepo)
            val result = fetchedArticlesUseCase(categoryName).first()

            assertEquals(pagingData, result)
        }
}
