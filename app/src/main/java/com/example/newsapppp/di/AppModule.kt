package com.example.newsapppp.di

import com.example.newsapppp.BuildConfig
import com.example.newsapppp.core.interceptor.ErrorsInterceptorContract
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

    @Provides
    @Singleton
    fun restErrorInterceptor(error: ErrorsInterceptorContract) = RestErrorInterceptor(error)

    @Provides
    @Singleton
    fun okHttpClient(error: RestErrorInterceptor) = OkHttpClient.Builder()
        .addNetworkInterceptor(error)
        .build()

    @Provides
    @Singleton
    fun retrofitInstance(okHttpClient: OkHttpClient): ApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(ApiService::class.java)
}
