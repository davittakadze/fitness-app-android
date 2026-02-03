package com.example.betteryou.feature.menu.data.di

import com.example.betteryou.feature.menu.data.repository.GoogleSignUpRepositoryImpl
import com.example.betteryou.feature.menu.domain.repository.GoogleSignUpRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModuleMenu {
    @Binds
    @Singleton
    abstract fun bindGoogleSignUpRepository(
        googleSignUpRepositoryImpl: GoogleSignUpRepositoryImpl
    ): GoogleSignUpRepository
}