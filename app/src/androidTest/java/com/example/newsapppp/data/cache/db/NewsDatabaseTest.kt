package com.example.newsapppp.data.cache.db

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.newsapppp.data.cache.db.dao.NewsDao
import com.example.newsapppp.data.cache.db.models.ArticleEntity
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

    private lateinit var productDao: NewsDao
    private lateinit var db: NewsDatabase

    @Before
    fun setUpDB() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, NewsDatabase::class.java).build()
        productDao = db.getNewsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun writeAndReadFact() = runBlocking {
        productDao.insertArticle(product)
        val getProduct = productDao.getAllArticles().first()

        val expected = listOf(product)

        assertEquals(expected.size, getProduct.size)
        assertEquals(expected.first(), getProduct.first())
    }

    @Test
    fun insertFactMustReturnValues() = runBlocking {
        productDao.insertArticle(product)
        val productFromDb = productDao.getAllArticles().first()

        val expected = listOf(product)
        assertEquals(expected.size, productFromDb.size)
        assertEquals(expected.first(), productFromDb.first())
    }

    @Test
    fun insertFactAndClearMustReturnEmptyValues() = runBlocking {
        productDao.insertArticle(product)
        val productFromDb = productDao.getAllArticles().first()

        val expected = listOf(product)

        assertEquals(expected.size, productFromDb.size)
        assertEquals(expected.first(), productFromDb.first())

        productDao.deleteAllArticle()
        val productFromDb2 = productDao.getAllArticles().first()

        assertEquals(0, productFromDb2.size)
    }

    @Test
    fun getFactFromEmptyDbMustReturnEmptyList() = runBlocking {
        val productFromDb = productDao.getAllArticles().first()

        assertEquals(0, productFromDb.size)
    }

    @Test
    fun deleteFactMustReturnEmptyList() = runBlocking {
        productDao.insertArticle(product)
        val productFromDb = productDao.getAllArticles().first()

        assertEquals(1, productFromDb.size)

        productDao.deleteArticle(product)
        val productFromDb2 = productDao.getAllArticles().first()

        assertEquals(0, productFromDb2.size)
    }

    @Test
    fun deleteNonExistingFactMustNotAffectDb() = runBlocking {
        productDao.insertArticle(product)
        val productFromDb = productDao.getAllArticles().first()

        assertEquals(1, productFromDb.size)

        val nonExistingProduct = product.copy(url = "non-existing-url")
        productDao.deleteArticle(nonExistingProduct)

        val productFromDb2 = productDao.getAllArticles().first()
        assertEquals(1, productFromDb2.size)
        assertEquals(product, productFromDb2.first())
    }

    private val product =
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
