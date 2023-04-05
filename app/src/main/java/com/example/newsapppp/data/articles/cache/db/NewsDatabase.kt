package com.example.newsapppp.data.articles.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapppp.data.articles.cache.db.dao.ArticleDao
import com.example.newsapppp.data.articles.cache.db.models.ArticleEntity

/**
 * Class for working with DB. Allow getting access to DAO from DB.
 * Contains tables from DB in param entities. Also need to update version in case of changing DB data.
 */
@Database(entities = [ArticleEntity::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getNewsDao(): ArticleDao

}
