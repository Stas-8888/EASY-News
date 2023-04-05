package com.example.newsapppp.di

import com.example.newsapppp.core.*
import com.example.newsapppp.core.dispatcher.DispatcherRepository
import com.example.newsapppp.core.dispatcher.DispatcherRepositoryContract
import com.example.newsapppp.core.network.NetworkHandler
import com.example.newsapppp.core.network.NetworkHandlerContract
import com.example.newsapppp.core.resources.ProvideResources
import com.example.newsapppp.core.resources.ProvideResourcesContract
import com.example.newsapppp.data.articles.cache.SharedPreferences
import com.example.newsapppp.domain.interactors.sharedpreferences.SharedPreferencesContract
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
    fun provideSharedPrefRepository(impl: SharedPreferences): SharedPreferencesContract = impl

    @Provides
    @Singleton
    fun providesNetworkHandler(impl: NetworkHandler): NetworkHandlerContract = impl
}
