package com.example.newsapppp.di

import com.example.newsapppp.data.repository.authentication.AuthenticationRepositoryImpl
import com.example.newsapppp.data.repository.authentication.ValidationRepositoryImpl
import com.example.newsapppp.domain.repository.AuthenticationRepository
import com.example.newsapppp.domain.repository.ValidationRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesValidationRepository(impl: ValidationRepositoryImpl): ValidationRepository = impl

    @Provides
    @Singleton
    fun providesAuthRepository(impl: AuthenticationRepositoryImpl): AuthenticationRepository = impl
}
