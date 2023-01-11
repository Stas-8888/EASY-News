package com.example.newsapppp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapppp.data.db.models.ArticleDbModel

//, FavoriteProductEntity::class
@Database(entities = [ArticleDbModel::class], version = 1)
abstract class NewsRoomDatabase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
//    abstract fun wishListDao(): WishListDAO
}
