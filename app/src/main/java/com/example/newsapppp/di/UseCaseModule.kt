package com.example.newsapppp.di

import com.example.newsapppp.domain.interactors.*
import com.example.newsapppp.domain.interactors.preference.*
import com.example.newsapppp.domain.interactors.registration.*
import com.example.newsapppp.domain.interactors.retrofit.GetNewsUseCase
import com.example.newsapppp.domain.interactors.retrofit.SearchNewsUseCase
import com.example.newsapppp.domain.interactors.room.DeleteAllUseCase
import com.example.newsapppp.domain.interactors.room.DeleteArticleUseCase
import com.example.newsapppp.domain.interactors.room.GetRoomArticleUseCase
import com.example.newsapppp.domain.repository.ArticleRepository
import com.example.newsapppp.domain.repository.DbRepository
import com.example.newsapppp.domain.repository.RegistrationRepository
import com.example.newsapppp.domain.repository.SharedPrefRepository
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
    fun provideValidateRepeatedPasswordUseCase(repo: RegistrationRepository): ValidateRepeatedPasswordUseCase =
        ValidateRepeatedPasswordUseCase(repo)

    @Provides
    @Singleton
    fun provideLoginUseCase(repo: RegistrationRepository): LoginUseCase =
        LoginUseCase(repo)

    @Provides
    @Singleton
    fun provideSignUpUseCase(repo: RegistrationRepository): SignUpUseCase =
        SignUpUseCase(repo)

    @Provides
    @Singleton
    fun provideFullNameUseCase(repo: RegistrationRepository): FullNameUseCase =
        FullNameUseCase(repo)

    @Provides
    @Singleton
    fun provideValidatePasswordUseCase(repo: RegistrationRepository): ValidatePasswordUseCase =
        ValidatePasswordUseCase(repo)

    @Provides
    @Singleton
    fun provideValidateEmailUseCase(repo: RegistrationRepository): ValidateEmailUseCase =
        ValidateEmailUseCase(repo)

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
