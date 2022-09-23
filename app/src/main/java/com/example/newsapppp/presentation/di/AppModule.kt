package com.example.newsapppp.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.newsapppp.data.db.NewsDao
import com.example.newsapppp.data.db.NewsRoomDatabase
import com.example.newsapppp.data.mapper.ArticleMapper
import com.example.newsapppp.data.mapper.NewsResponseMapper
import com.example.newsapppp.data.network.ApiService
import com.example.newsapppp.data.repository.ArticleRepositoryImpl
import com.example.newsapppp.data.repository.DbRepositoryImpl
import com.example.newsapppp.data.repository.SharedPrefRepositoryImpl
import com.example.newsapppp.domain.repository.ArticleRepository
import com.example.newsapppp.domain.repository.DbRepository
import com.example.newsapppp.domain.repository.SharedPrefRepository
import com.example.newsapppp.presentation.mapper.ArticleMapperToModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * A component whose lifetime is the life of the application.
 */
private const val BASE_URL = "https://newsapi.org"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun retrofitInstance(): ApiService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

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
