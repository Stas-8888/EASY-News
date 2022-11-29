package com.example.newsapppp.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.newsapppp.data.db.NewsDao
import com.example.newsapppp.data.db.NewsRoomDatabase
import com.example.newsapppp.data.mapper.ArticleMapper
import com.example.newsapppp.data.mapper.NewsResponseMapper
import com.example.newsapppp.presentation.mapper.ArticleMapperToModel
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
            NewsRoomDatabase::class.java,
            "article_database"
        ).build()

    @Provides
    @Singleton
    fun provideArticleDao(appDatabase: NewsRoomDatabase): NewsDao {
        return appDatabase.getNewsDao()
    }
}
