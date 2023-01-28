package com.example.newsapppp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapppp.data.db.models.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao

}
