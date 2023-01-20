package com.example.newsapppp.di

import com.example.newsapppp.domain.interactors.*
import com.example.newsapppp.domain.interactors.preference.*
import com.example.newsapppp.domain.interactors.firebase.*
import com.example.newsapppp.domain.interactors.retrofit.GetNewsUseCase
import com.example.newsapppp.domain.interactors.retrofit.SearchNewsUseCase
import com.example.newsapppp.domain.interactors.room.DeleteAllUseCase
import com.example.newsapppp.domain.interactors.room.DeleteArticleUseCase
import com.example.newsapppp.domain.interactors.room.GetRoomArticleUseCase
import com.example.newsapppp.domain.repository.ArticleRepositoryContract
import com.example.newsapppp.domain.repository.DataBaseRepositoryContract
import com.example.newsapppp.domain.repository.FirebaseRepositoryContract
import com.example.newsapppp.domain.repository.SharedPrefRepositoryContract
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
    fun provideValidateRepeatedPasswordUseCase(repo: FirebaseRepositoryContract): ValidateRepeatedPasswordUseCase =
        ValidateRepeatedPasswordUseCase(repo)

    @Provides
    @Singleton
    fun provideLoginUseCase(repo: FirebaseRepositoryContract): SignInUseCase =
        SignInUseCase(repo)

    @Provides
    @Singleton
    fun provideSignUpUseCase(repo: FirebaseRepositoryContract): SignUpUseCase =
        SignUpUseCase(repo)

    @Provides
    @Singleton
    fun provideFullNameUseCase(repo: FirebaseRepositoryContract): FullNameUseCase =
        FullNameUseCase(repo)

    @Provides
    @Singleton
    fun provideValidatePasswordUseCase(repo: FirebaseRepositoryContract): ValidatePasswordUseCase =
        ValidatePasswordUseCase(repo)

    @Provides
    @Singleton
    fun provideValidateEmailUseCase(repo: FirebaseRepositoryContract): ValidateEmailUseCase =
        ValidateEmailUseCase(repo)

    @Provides
    @Singleton
    fun provideDeleteAllUseCase(repo: DataBaseRepositoryContract): DeleteAllUseCase =
        DeleteAllUseCase(repo)

    @Provides
    @Singleton
    fun provideDeleteArticleUseCase(repo: DataBaseRepositoryContract): DeleteArticleUseCase =
        DeleteArticleUseCase(repo)

    @Provides
    @Singleton
    fun provideRoomGetArticleUseCase(repo: DataBaseRepositoryContract): GetRoomArticleUseCase =
        GetRoomArticleUseCase(repo)

    @Provides
    @Singleton
    fun provideGetNewsUseCase(repo: ArticleRepositoryContract): GetNewsUseCase =
        GetNewsUseCase(repo)

    @Provides
    @Singleton
    fun provideSearchNewsUseCase(repo: ArticleRepositoryContract): SearchNewsUseCase =
        SearchNewsUseCase(repo)

    @Provides
    @Singleton
    fun provideSaveFavoriteUseCase(repo: SharedPrefRepositoryContract): SaveFavoriteUseCase =
        SaveFavoriteUseCase(repo)

    @Provides
    @Singleton
    fun provideGetFavoriteUseCase(repo: SharedPrefRepositoryContract): GetFavoriteUseCase =
        GetFavoriteUseCase(repo)

    @Provides
    @Singleton
    fun provideGetCountryFlagUseCase(repo: SharedPrefRepositoryContract): GetCountryFlagUseCase =
        GetCountryFlagUseCase(repo)

    @Provides
    @Singleton
    fun provideSaveCountryFlagUseCase(repo: SharedPrefRepositoryContract): SaveCountryFlagUseCase =
        SaveCountryFlagUseCase(repo)

    @Provides
    @Singleton
    fun provideGetSwitchPositionUseCase(repo: SharedPrefRepositoryContract): GetSwitchPositionUseCase =
        GetSwitchPositionUseCase(repo)

    @Provides
    @Singleton
    fun provideSaveSwitchPositionUseCase(repo: SharedPrefRepositoryContract): SaveSwitchPositionUseCase =
        SaveSwitchPositionUseCase(repo)
}
