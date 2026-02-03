package com.example.data.di

import com.example.betteryou.data.local.DatastoreManagerImpl
import com.example.betteryou.domain.repository.DatastoreRepository
import com.example.data.repository.login.LogInRepositoryImpl
import com.example.domain.repository.login.LogInRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLogInRepository(
        logInRepositoryImpl: LogInRepositoryImpl
    ): LogInRepository
}