package com.example.newsapppp.di

import com.example.newsapppp.data.authentication.AuthenticationRepository
import com.example.newsapppp.data.authentication.ValidationRepository
import com.example.newsapppp.domain.interactors.authentication.AuthenticationRepositoryContract
import com.example.newsapppp.domain.interactors.authentication.validation.ValidationRepositoryContract
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
    fun providesValidationRepository(impl: ValidationRepository): ValidationRepositoryContract =
        impl

    @Provides
    @Singleton
    fun providesAuthRepository(impl: AuthenticationRepository): AuthenticationRepositoryContract =
        impl
}
