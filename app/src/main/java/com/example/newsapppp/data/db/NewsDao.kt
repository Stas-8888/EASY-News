package com.example.newsapppp.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticle(article: ArticleDbModel)

    @Delete
    suspend fun deleteArticle(article: ArticleDbModel)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<ArticleDbModel>>

    @Query("DELETE FROM articles")
    suspend fun deleteAllArticle()
}
