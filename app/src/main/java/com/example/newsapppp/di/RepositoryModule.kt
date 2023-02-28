package com.example.newsapppp.di

import com.example.newsapppp.core.*
import com.example.newsapppp.core.dispatcher.DispatcherRepository
import com.example.newsapppp.core.dispatcher.DispatcherRepositoryContract
import com.example.newsapppp.core.network.NetworkHandler
import com.example.newsapppp.core.network.NetworkHandlerContract
import com.example.newsapppp.core.resources.ProvideResources
import com.example.newsapppp.core.resources.ProvideResourcesContract
import com.example.newsapppp.data.remote.interceptor.ErrorsInterceptor
import com.example.newsapppp.data.remote.interceptor.ErrorsInterceptorContract
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
    fun providesNetworkHandler(impl: NetworkHandler): NetworkHandlerContract = impl

    @Provides
    @Singleton
    fun providesErrorsInterceptor(impl: ErrorsInterceptor): ErrorsInterceptorContract = impl

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
    fun provideArticleRepository(impl: ArticleRemoteRepository): ArticleRemoteRepositoryContract = impl

    @Provides
    @Singleton
    fun provideDbRepository(impl: ArticleLocalSourceRepository): ArticleLocalSourceRepositoryContract = impl

    @Provides
    @Singleton
    fun provideSharedPrefRepository(impl: SharedPrefRepository): SharedPrefRepositoryContract = impl
}
