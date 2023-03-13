package com.example.newsapppp.domain.interactors.articleremote

import androidx.paging.PagingData
import com.example.newsapppp.domain.model.ArticleModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetNewsUseCaseTest {

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

        whenever(mockRepo.getNews(categoryName)).thenReturn(pagingSource)

        val getNewsUseCase = GetNewsUseCase(mockRepo)
        val result = getNewsUseCase(categoryName).first()
        assertEquals(pagingData, result)
    }
}
