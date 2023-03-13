package com.example.newsapppp.di

import android.content.Context
import androidx.room.Room
import com.example.newsapppp.data.cache.db.dao.NewsDao
import com.example.newsapppp.data.cache.db.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideArticleDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "article_database"
        ).build()

    @Provides
    @Singleton
    fun provideArticleDao(appDatabase: NewsDatabase): NewsDao {
        return appDatabase.getNewsDao()
    }
}
