package com.example.newsapppp.di

import com.example.newsapppp.data.mapper.ArticleMapper
import com.example.newsapppp.data.mapper.NewsResponseMapper
import com.example.newsapppp.presentation.mapper.ArticleMapperToModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun provideArticleMapper(): ArticleMapper = ArticleMapper()

    @Provides
    @Singleton
    fun provideArticleMapperToModel(): ArticleMapperToModel = ArticleMapperToModel()

    @Provides
    @Singleton
    fun provideNewsResponseMapper(articleMapper: ArticleMapper): NewsResponseMapper =
        NewsResponseMapper(articleMapper)
}
