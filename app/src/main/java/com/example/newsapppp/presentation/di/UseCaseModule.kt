package com.example.newsapppp.presentation.di

import com.example.newsapppp.domain.repository.ArticleRepository
import com.example.newsapppp.domain.repository.DbRepository
import com.example.newsapppp.domain.repository.SharedPrefRepository
import com.example.newsapppp.domain.interactors.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideDeleteAllUseCase(repo: DbRepository): DeleteAllUseCase =
        DeleteAllUseCase(repo)

    @Provides
    @Singleton
    fun provideDeleteArticleUseCase(repo: DbRepository): DeleteArticleUseCase =
        DeleteArticleUseCase(repo)

    @Provides
    @Singleton
    fun provideRoomGetArticleUseCase(repo: DbRepository): GetRoomArticleUseCase =
        GetRoomArticleUseCase(repo)

    @Provides
    @Singleton
    fun provideGetNewsUseCase(repo: ArticleRepository): GetNewsUseCase =
        GetNewsUseCase(repo)

    @Provides
    @Singleton
    fun provideSearchNewsUseCase(repo: ArticleRepository): SearchNewsUseCase =
        SearchNewsUseCase(repo)

    @Provides
    @Singleton
    fun provideSaveFavoriteUseCase(repo: SharedPrefRepository): SaveFavoriteUseCase =
        SaveFavoriteUseCase(repo)

    @Provides
    @Singleton
    fun provideGetFavoriteUseCase(repo: SharedPrefRepository): GetFavoriteUseCase =
        GetFavoriteUseCase(repo)

    @Provides
    @Singleton
    fun provideGetCountryFlagUseCase(repo: SharedPrefRepository): GetCountryFlagUseCase =
        GetCountryFlagUseCase(repo)

    @Provides
    @Singleton
    fun provideSaveCountryFlagUseCase(repo: SharedPrefRepository): SaveCountryFlagUseCase =
        SaveCountryFlagUseCase(repo)

    @Provides
    @Singleton
    fun provideGetSwitchPositionUseCase(repo: SharedPrefRepository): GetSwitchPositionUseCase =
        GetSwitchPositionUseCase(repo)

    @Provides
    @Singleton
    fun provideSaveSwitchPositionUseCase(repo: SharedPrefRepository): SaveSwitchPositionUseCase =
        SaveSwitchPositionUseCase(repo)
}
