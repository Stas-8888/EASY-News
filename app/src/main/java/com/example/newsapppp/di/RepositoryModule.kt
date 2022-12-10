package com.example.newsapppp.di

import com.example.newsapppp.core.Base
import com.example.newsapppp.core.DispatchersList
import com.example.newsapppp.data.repository.ArticleRepositoryImpl
import com.example.newsapppp.data.repository.DbRepositoryImpl
import com.example.newsapppp.data.repository.RegistrationRepositoryImpl
import com.example.newsapppp.data.repository.SharedPrefRepositoryImpl
import com.example.newsapppp.domain.repository.ArticleRepository
import com.example.newsapppp.domain.repository.DbRepository
import com.example.newsapppp.domain.repository.RegistrationRepository
import com.example.newsapppp.domain.repository.SharedPrefRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesDispatchersList(impl: Base): DispatchersList = impl

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesAuthRepository(impl: RegistrationRepositoryImpl): RegistrationRepository = impl

    @Provides
    @Singleton
    fun provideArticleRepository(impl: ArticleRepositoryImpl): ArticleRepository = impl

    @Provides
    @Singleton
    fun provideDbRepository(impl: DbRepositoryImpl): DbRepository = impl

    @Provides
    @Singleton
    fun provideSharedPrefRepository(impl: SharedPrefRepositoryImpl): SharedPrefRepository = impl
}
