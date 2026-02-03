package com.example.betteryou.repository

import com.example.betteryou.data.local.DatastoreManagerImpl
import com.example.betteryou.data.repository.register.RegisterRepositoryImpl
import com.example.betteryou.domain.repository.DatastoreRepository
import com.example.betteryou.domain.repository.register.RegisterRepository
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
    abstract fun bindRegisterRepository(
        registerRepositoryImpl: RegisterRepositoryImpl
    ): RegisterRepository

    @Binds
    @Singleton
    abstract fun bindDatastoreRepository(
        datastoreRepositoryImpl: DatastoreManagerImpl
    ): DatastoreRepository

}