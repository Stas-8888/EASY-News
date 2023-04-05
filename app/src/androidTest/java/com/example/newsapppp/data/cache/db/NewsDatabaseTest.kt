package com.example.newsapppp.data.cache.db

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.newsapppp.data.articles.cache.db.NewsDatabase
import com.example.newsapppp.data.articles.cache.db.dao.ArticleDao
import com.example.newsapppp.data.articles.cache.db.models.ArticleEntity
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class NewsDatabaseTest {

    private lateinit var articleDao: ArticleDao
    private lateinit var db: NewsDatabase

    @Before
    fun setUpDB() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, NewsDatabase::class.java).build()
        articleDao = db.getNewsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun writeAndReadArticle() = runBlocking {
        articleDao.insertArticle(article)
        val getArticle = articleDao.getAllArticles().first()

        val expected = listOf(article)

        assertEquals(expected.size, getArticle.size)
        assertEquals(expected.first(), getArticle.first())
    }

    @Test
    fun insertArticleMustReturnValues() = runBlocking {
        articleDao.insertArticle(article)
        val articleFromDb = articleDao.getAllArticles().first()

        val expected = listOf(article)
        assertEquals(expected.size, articleFromDb.size)
        assertEquals(expected.first(), articleFromDb.first())
    }

    @Test
    fun insertArticleAndClearMustReturnEmptyValues() = runBlocking {
        articleDao.insertArticle(article)
        val articleFromDb = articleDao.getAllArticles().first()

        val expected = listOf(article)

        assertEquals(expected.size, articleFromDb.size)
        assertEquals(expected.first(), articleFromDb.first())

        articleDao.deleteAllArticle()
        val articleFromDb2 = articleDao.getAllArticles().first()

        assertEquals(0, articleFromDb2.size)
    }

    @Test
    fun getArticleFromEmptyDbMustReturnEmptyList() = runBlocking {
        val articleFromDb = articleDao.getAllArticles().first()

        assertEquals(0, articleFromDb.size)
    }

    @Test
    fun deleteArticleMustReturnEmptyList() = runBlocking {
        articleDao.insertArticle(article)
        val articleFromDb = articleDao.getAllArticles().first()

        assertEquals(1, articleFromDb.size)

        articleDao.deleteArticle(article)
        val articleFromDb2 = articleDao.getAllArticles().first()

        assertEquals(0, articleFromDb2.size)
    }

    @Test
    fun deleteNonExistingArticleMustNotAffectDb() = runBlocking {
        articleDao.insertArticle(article)
        val articleFromDb = articleDao.getAllArticles().first()

        assertEquals(1, articleFromDb.size)

        val nonExistingArticle = article.copy(url = "non-existing-url")
        articleDao.deleteArticle(nonExistingArticle)

        val articleFromDb2 = articleDao.getAllArticles().first()
        assertEquals(1, articleFromDb2.size)
        assertEquals(article, articleFromDb2.first())
    }

    private val article =
        ArticleEntity(
            url = "",
            author = "null",
            content = "null",
            description = "null",
            publishedAt = "null",
            title = "null",
            urlToImage = "null"
        )
}
