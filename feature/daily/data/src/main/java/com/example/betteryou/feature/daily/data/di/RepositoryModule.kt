package com.example.betteryou.feature.daily.data.di

import com.example.betteryou.feature.daily.data.repository.GetDataRepositoryImpl
import com.example.betteryou.feature.daily.domain.repository.GetDataRepository
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
    abstract fun bindGetDataRepository(
        getDataRepositoryImpl: GetDataRepositoryImpl,
    ): GetDataRepository
}