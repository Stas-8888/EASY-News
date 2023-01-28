package com.example.newsapppp.data.db

import androidx.room.*
import com.example.newsapppp.data.db.models.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticle(article: ArticleEntity)

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Delete
    suspend fun deleteArticle(article: ArticleEntity)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAllArticle()

    companion object {
        const val TABLE_NAME = "articles"
    }
}
