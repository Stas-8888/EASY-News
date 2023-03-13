package com.example.newsapppp.di

import com.example.newsapppp.core.*
import com.example.newsapppp.core.dispatcher.DispatcherRepository
import com.example.newsapppp.core.dispatcher.DispatcherRepositoryContract
import com.example.newsapppp.core.network.NetworkHandler
import com.example.newsapppp.core.network.NetworkHandlerContract
import com.example.newsapppp.core.resources.ProvideResources
import com.example.newsapppp.core.resources.ProvideResourcesContract
import com.example.newsapppp.data.authentication.AuthenticationRepository
import com.example.newsapppp.data.authentication.ValidationRepository
import com.example.newsapppp.data.cache.ArticleCache
import com.example.newsapppp.data.cache.SharedPreferences
import com.example.newsapppp.data.remote.ArticleRemote
import com.example.newsapppp.data.remote.interceptor.ErrorsInterceptor
import com.example.newsapppp.data.remote.interceptor.ErrorsInterceptorContract
import com.example.newsapppp.domain.interactors.articlecache.ArticleCacheContract
import com.example.newsapppp.domain.interactors.articleremote.ArticleRemoteContract
import com.example.newsapppp.domain.interactors.authentication.AuthenticationRepositoryContract
import com.example.newsapppp.domain.interactors.authentication.ValidationRepositoryContract
import com.example.newsapppp.domain.interactors.sharedpreferences.SharedPreferencesContract
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
    fun provideArticleRepository(impl: ArticleRemote): ArticleRemoteContract = impl

    @Provides
    @Singleton
    fun provideDbRepository(impl: ArticleCache): ArticleCacheContract = impl

    @Provides
    @Singleton
    fun provideSharedPrefRepository(impl: SharedPreferences): SharedPreferencesContract = impl
}
