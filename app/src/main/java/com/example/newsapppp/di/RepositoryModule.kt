package com.example.newsapppp.di

import com.example.newsapppp.core.Base
import com.example.newsapppp.core.Dispatchers
import com.example.newsapppp.core.ManageResources
import com.example.newsapppp.data.repository.ArticleRepository
import com.example.newsapppp.data.repository.DataBaseRepository
import com.example.newsapppp.data.repository.FirebaseRepository
import com.example.newsapppp.data.repository.SharedPrefRepository
import com.example.newsapppp.domain.repository.ValidationRepository
import com.example.newsapppp.domain.repository.ValidationRepositoryContract
import com.example.newsapppp.domain.repository.ArticleRepositoryContract
import com.example.newsapppp.domain.repository.DataBaseRepositoryContract
import com.example.newsapppp.domain.repository.FirebaseRepositoryContract
import com.example.newsapppp.domain.repository.SharedPrefRepositoryContract
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
    fun providesManageResources(impl: ManageResources.Base): ManageResources = impl

    @Provides
    @Singleton
    fun providesDispatchersList(impl: Base): Dispatchers = impl

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesValidationRepository(impl: ValidationRepository): ValidationRepositoryContract =
        impl

    @Provides
    @Singleton
    fun providesAuthRepository(impl: FirebaseRepository): FirebaseRepositoryContract = impl

    @Provides
    @Singleton
    fun provideArticleRepository(impl: ArticleRepository): ArticleRepositoryContract = impl

    @Provides
    @Singleton
    fun provideDbRepository(impl: DataBaseRepository): DataBaseRepositoryContract = impl

    @Provides
    @Singleton
    fun provideSharedPrefRepository(impl: SharedPrefRepository): SharedPrefRepositoryContract = impl
}
