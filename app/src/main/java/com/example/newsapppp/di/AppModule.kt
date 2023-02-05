package com.example.newsapppp.di

import com.example.newsapppp.BuildConfig
import com.example.newsapppp.core.interceptor.RestErrorInterceptor
import com.example.newsapppp.data.network.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    private val restErrorInterceptor = RestErrorInterceptor()

    @Singleton
    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(restErrorInterceptor)
        .build()

    @Provides
    @Singleton
    fun retrofitInstance(): ApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(ApiService::class.java)
}
