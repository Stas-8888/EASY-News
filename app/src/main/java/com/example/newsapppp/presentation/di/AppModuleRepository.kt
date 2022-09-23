package com.example.newsapppp.presentation.di

import android.content.Context
import com.example.newsapppp.data.db.NewsDao
import com.example.newsapppp.data.mapper.ArticleMapper
import com.example.newsapppp.data.mapper.NewsResponseMapper
import com.example.newsapppp.data.network.ApiService
import com.example.newsapppp.data.repository.ArticleRepositoryImpl
import com.example.newsapppp.data.repository.DbRepositoryImpl
import com.example.newsapppp.data.repository.SharedPrefRepositoryImpl
import com.example.newsapppp.domain.repository.ArticleRepository
import com.example.newsapppp.domain.repository.DbRepository
import com.example.newsapppp.domain.repository.SharedPrefRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModuleRepository {

    @Provides
    @Singleton
    fun provideArticleRepository(
        apiService: ApiService,
        newsResponseMapper: NewsResponseMapper
    ): ArticleRepository {
        return ArticleRepositoryImpl(apiService, newsResponseMapper)
    }

    @Provides
    @Singleton
    fun provideDbRepository(
        newsDao: NewsDao,
        mapper: ArticleMapper,
        newsResponseMapper: NewsResponseMapper
    ): DbRepository {
        return DbRepositoryImpl(newsDao, mapper, newsResponseMapper)
    }

    @Provides
    @Singleton
    fun provideSharedPrefRepository(
        @ApplicationContext context: Context
    ): SharedPrefRepository {
        return SharedPrefRepositoryImpl(context)
    }
}
