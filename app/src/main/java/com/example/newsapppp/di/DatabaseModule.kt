package com.example.newsapppp.di

import android.content.Context
import androidx.room.Room
import com.example.newsapppp.data.articles.cache.ArticleCacheRepositoryImpl
import com.example.newsapppp.data.articles.cache.db.NewsDatabase
import com.example.newsapppp.data.articles.cache.db.dao.ArticleDao
import com.example.newsapppp.domain.interactors.articles.cache.ArticleCacheRepository
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
    fun provideArticleDao(appDatabase: NewsDatabase): ArticleDao {
        return appDatabase.getNewsDao()
    }

    @Provides
    @Singleton
    fun provideDbRepository(impl: ArticleCacheRepositoryImpl): ArticleCacheRepository = impl
}
