package com.example.newsapppp.di

import com.example.newsapppp.common.*
import com.example.newsapppp.common.dispatcher.DispatcherRepositoryImpl
import com.example.newsapppp.common.dispatcher.DispatcherRepository
import com.example.newsapppp.common.network.NetworkHandler
import com.example.newsapppp.common.network.NetworkHandlerRepository
import com.example.newsapppp.common.resources.ProvideResourcesRepositoryImpl
import com.example.newsapppp.common.resources.ProvideResourcesRepository
import com.example.newsapppp.data.source.cache.SharedPreferencesRepositoryImpl
import com.example.newsapppp.domain.repository.SharedPreferencesRepository
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
