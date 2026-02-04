package com.betteryou.feature.register.data.di

import com.betteryou.feature.register.data.repository.RegisterRepositoryImpl
import com.betteryou.feature.register.domain.repository.RegisterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModuleRegister {
    @Binds
    @Singleton
    abstract fun bindRegisterRepository(
        registerRepositoryImpl: RegisterRepositoryImpl
    ): RegisterRepository

}