package com.example.newsapppp.di

import com.example.newsapppp.BuildConfig
import com.example.newsapppp.data.repository.ArticleRepositoryImpl
import com.example.newsapppp.data.interceptor.ErrorsInterceptor
import com.example.newsapppp.data.interceptor.ErrorsInterceptorRepository
import com.example.newsapppp.data.interceptor.RestErrorInterceptor
import com.example.newsapppp.data.source.remote.service.ApiService
import com.example.newsapppp.domain.repository.ArticleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val PARAMETER_API_KEY = "apikey"
const val TIME_OUT: Long = 1

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(errorInterceptor: RestErrorInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()

        // Add API key to URL query parameter
        builder.addInterceptor { chain ->
            val originalRequest = chain.request()
            val url = originalRequest.url.newBuilder()
                .addQueryParameter(PARAMETER_API_KEY, BuildConfig.API_KEY)
                .build()
            val requestBuilder = originalRequest.newBuilder().url(url)
            chain.proceed(requestBuilder.build())
        }

        // Configure timeouts
        builder.connectTimeout(TIME_OUT, TimeUnit.MINUTES)
        builder.readTimeout(TIME_OUT, TimeUnit.MINUTES)

        // Add logging interceptor
        builder.addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })

        // Add error interceptor
        builder.addNetworkInterceptor(errorInterceptor)

        return builder.build()
    }

    @Provides
    @Singleton
    fun retrofitInstance(okHttpClient: OkHttpClient): ApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(ApiService::class.java)

    @Provides
    @Singleton
    fun restErrorInterceptor(error: ErrorsInterceptorRepository) = RestErrorInterceptor(error)

    @Provides
    @Singleton
    fun providesErrorsInterceptor(impl: ErrorsInterceptor): ErrorsInterceptorRepository = impl

    @Provides
    @Singleton
    fun provideArticleRemote(impl: ArticleRepositoryImpl): ArticleRepository = impl
}
