package com.example.newsapppp.data.cache.db.dao

import androidx.room.*
import com.example.newsapppp.data.cache.db.models.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    /**
     * Insert [ArticleEntity] to DB using REPLACE strategy -
     * OnConflict strategy constant to replace the old data and continue the transaction.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleEntity)

    /**
     * @return List[ArticleEntity] from DB.
     * If DB is empty - return empty list
     */
    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    /**
     * Clear Article data from DB
     */
    @Delete
    suspend fun deleteArticle(article: ArticleEntity)

    /**
     * Clear all data from DB
     */
    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAllArticle()

    companion object {
        const val TABLE_NAME = "articles"
    }
}
