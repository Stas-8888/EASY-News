package com.example.newsapppp.domain.use_case.articlecache

import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.domain.repository.ArticleCacheRepository
import com.example.newsapppp.domain.use_case.articles.cache.InsertArticleUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class InsertArticleUseCaseTest {

    @Mock
    private lateinit var mockRepository: ArticleCacheRepository

    private lateinit var useCase: InsertArticleUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        useCase = InsertArticleUseCase(mockRepository)
    }

    @Test
    fun `insertArticle should return success`() = runBlocking {
        // Given
        val article = ArticleModel(
            title = "Mock article title",
            description = "Mock article description",
            url = "http://example.com",
            urlToImage = "http://example.com/image.jpg",
            publishedAt = "2023-03-08T12:00:00Z",
            content = "Mock article content",
            author = "Mock author"
        )
//        `when`(mockRepository.insertArticle(article)).thenReturn(Unit)

        // When
        val result = useCase.invoke(article)

        // Then
        assertNotNull(result)
        verify(mockRepository, times(1)).insertArticle(article)
        verifyNoMoreInteractions(mockRepository)
    }

    @Test(expected = Exception::class)
    fun `insertArticle with invalid data should throw exception`() = runBlocking {
        // Given
        val invalidArticle = ArticleModel(
            title = "Mock article title",
            description = "Mock article description",
            url = "http://example.com",
            urlToImage = "http://example.com/image.jpg",
            publishedAt = "2023-03-08T12:00:00Z",
            content = "Mock article content",
            author = "Mock author"
        )
        `when`(mockRepository.insertArticle(invalidArticle)).thenThrow(Exception("Invalid data"))

        // When
        useCase.invoke(invalidArticle)

        // Then
        // Expecting an exception to be thrown
    }

    @Test(expected = Exception::class)
    fun `insertArticle with null data should throw exception`() = runBlocking {
        // Given
        val nullArticle: ArticleModel? = null
        `when`(mockRepository.insertArticle(nullArticle!!)).thenThrow(Exception("Null data"))

        // When
        useCase.invoke(nullArticle)

        // Then
        // Expecting an exception to be thrown
    }



//    private val mockRepo = mock<ArticleCacheRepository>()
//
//    @Test
//    fun `inserted article should be returned in getAllArticles`() = runBlocking {
//        val mockArticleModel = ArticleModel(
//            title = "Mock article title",
//            description = "Mock article description",
//            url = "http://example.com",
//            urlToImage = "http://example.com/image.jpg",
//            publishedAt = "2023-03-08T12:00:00Z",
//            content = "Mock article content",
//            author = "Mock author"
//        )
//
//        // Mock the repository to return a flow of a list with the mock article
//        Mockito.`when`(mockRepo.getAllArticles()).thenReturn(flowOf(listOf(mockArticleModel)))
//
//        // Call the use case to insert the mock article
//        val insertArticleUseCase = InsertArticleUseCase(mockRepo)
//        insertArticleUseCase.invoke(mockArticleModel)
//
//        // Verify that the mock article is returned in getAllArticles
//        val expected = listOf(mockArticleModel)
//        val actual = mockRepo.getAllArticles().first()
//
//        assertEquals(expected, actual)
//    }
//
//    @Test
//    fun insertArticleUseCase() = runBlocking {
//        val mockArticleModel = ArticleModel(
//            title = "Mock article title",
//            description = "Mock article description",
//            url = "http://example.com",
//            urlToImage = "http://example.com/image.jpg",
//            publishedAt = "2023-03-08T12:00:00Z",
//            content = "Mock article content",
//            author = "Mock author"
//        )
//
//        val fakeRepo = mockRepository
//        val insertArticleUseCase = InsertArticleUseCase(fakeRepo)
//
//        insertArticleUseCase.invoke(mockArticleModel)
//
////        val expected = listOf(mockArticleModel)
//        val expected = fakeRepo.insertArticle(mockArticleModel)
//        val actual = fakeRepo.getAllArticles().first()
//
//        assertEquals(expected, actual)
//    }
//
//    private class FakeMainRepository : ArticleCacheRepository {
//        private val articles = mutableListOf<ArticleModel>()
//
//        override suspend fun insertArticle(article: ArticleModel) {
//            articles.add(article)
//        }
//
//        override suspend fun deleteArticle(article: ArticleModel) {
//            articles.remove(article)
//        }
//
//        override fun getAllArticles(): Flow<List<ArticleModel>> = flow {
//            emit(articles)
//        }
//
//        override suspend fun deleteAllArticle() {
//            articles.clear()
//        }
//    }

}
