package com.example.newsapppp.data.db

import androidx.room.*
import com.example.newsapppp.data.db.models.ArticleDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticle(article: ArticleDbModel)

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllArticles(): Flow<List<ArticleDbModel>>

    @Delete
    suspend fun deleteArticle(article: ArticleDbModel)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAllArticle()

    companion object {
        const val TABLE_NAME = "articles"
    }
}
