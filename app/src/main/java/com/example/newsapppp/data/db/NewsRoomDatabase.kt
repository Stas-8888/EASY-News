package com.example.newsapppp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArticleDbModel::class], version = 1)
abstract class NewsRoomDatabase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
}
