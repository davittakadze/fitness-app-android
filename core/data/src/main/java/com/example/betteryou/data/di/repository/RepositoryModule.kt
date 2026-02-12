package com.example.betteryou.data.di.repository

import com.example.betteryou.data.local.DatastoreManagerImpl
import com.example.betteryou.data.repository.GetUserIdRepositoryImpl
import com.example.betteryou.domain.repository.DatastoreRepository
import com.example.betteryou.domain.repository.GetUserIdRepository
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
    abstract fun bindDatastoreRepository(
        datastoreRepositoryImpl: DatastoreManagerImpl
    ): DatastoreRepository
    @Binds
    @Singleton
    abstract fun bindUserIdRepository(
        userIdRepositoryImpl: GetUserIdRepositoryImpl
    ): GetUserIdRepository
}