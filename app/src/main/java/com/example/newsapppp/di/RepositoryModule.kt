package com.example.newsapppp.di

import com.example.newsapppp.core.*
import com.example.newsapppp.core.dispatcher.DispatcherRepositoryImpl
import com.example.newsapppp.core.dispatcher.DispatcherRepository
import com.example.newsapppp.core.network.NetworkHandler
import com.example.newsapppp.core.network.NetworkHandlerRepository
import com.example.newsapppp.core.resources.ProvideResourcesRepositoryImpl
import com.example.newsapppp.core.resources.ProvideResourcesRepository
import com.example.newsapppp.data.articles.cache.SharedPreferencesRepositoryImpl
import com.example.newsapppp.domain.interactors.sharedpreferences.SharedPreferencesRepository
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
    fun providesManageResources(impl: ProvideResourcesRepositoryImpl): ProvideResourcesRepository = impl

    @Provides
    @Singleton
    fun providesDispatchersList(impl: DispatcherRepositoryImpl): DispatcherRepository = impl

    @Provides
    @Singleton
    fun provideSharedPrefRepository(impl: SharedPreferencesRepositoryImpl): SharedPreferencesRepository = impl

    @Provides
    @Singleton
    fun providesNetworkHandler(impl: NetworkHandler): NetworkHandlerRepository = impl
}
