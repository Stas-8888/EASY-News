package com.example.newsapppp.data.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.newsapppp.data.db.models.ArticleDbModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class NewsDatabaseTest {
    private lateinit var newsDao: NewsDao
    private lateinit var db: NewsDatabase

    @Before
    fun setUpDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NewsDatabase::class.java).build()
        newsDao = db.getNewsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun writeAndReadNews() = runBlocking {
        newsDao.insertArticle(news)
        val getNews = newsDao.getAllArticles()

        assertEquals(news.size, getNews.size)
        assertEquals(news.first(), getNews.first())
    }

    @Test
    fun insertNewsMustReturnValues() = runBlocking {
        newsDao.insertArticle(news)
        val newsFromDb = newsDao.getAllArticles()

        assertEquals(news.size, newsFromDb.size)
        assertEquals(news.first(), newsFromDb.first())
    }

    @Test
    fun insertEmptyNewsMustReturnEmptyValues() = runBlocking {
        newsDao.insertArticle(listOf())
        val currenciesFromDb = newsDao.getAllArticles()

        assertEquals(0, currenciesFromDb.size)
    }

    @Test
    fun insertNewsAndClearMustReturnEmptyValues() = runBlocking {
        newsDao.insertArticle(news)
        val newsFromDb = newsDao.getAllArticles()

        assertEquals(news.size, newsFromDb.size)
        assertEquals(news.first(), newsFromDb.first())

        newsDao.deleteAllArticle()
        val newsFromDb2 = newsDao.getAllArticles()

        assertEquals(0, newsFromDb2.size)
    }

    @Test
    fun getNewsFromEmptyDbMustReturnEmptyList() = runBlocking {
        val newsFromDb = newsDao.getAllArticles()

        assertEquals(0, newsFromDb.size)
    }

    private val news = listOf(
        ArticleDbModel(
            url = "someURl",
            author = "Ken",
            content = "Dayle",
            description = "Tramp",
            publishedAt = "21",
            title = "USA",
            urlToImage = "someURl",
        )
    )
}
