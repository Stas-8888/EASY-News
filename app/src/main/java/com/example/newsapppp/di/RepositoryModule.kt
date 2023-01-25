package com.example.newsapppp.di

import com.example.newsapppp.core.DispatcherRepositoryContract
import com.example.newsapppp.core.ProvideResourcesContract
import com.example.newsapppp.data.repository.*
import com.example.newsapppp.domain.repository.*
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
    fun providesManageResources(impl: ProvideResources): ProvideResourcesContract = impl

    @Provides
    @Singleton
    fun providesDispatchersList(impl: DispatcherRepository): DispatcherRepositoryContract = impl

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesValidationRepository(impl: ValidationRepository): ValidationRepositoryContract =
        impl

    @Provides
    @Singleton
    fun providesAuthRepository(impl: AuthenticationRepository): AuthenticationRepositoryContract =
        impl

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
