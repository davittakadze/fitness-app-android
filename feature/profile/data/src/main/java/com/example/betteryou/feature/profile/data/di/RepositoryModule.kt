package com.example.betteryou.feature.profile.data.di

import com.example.betteryou.feature.profile.data.repository.UserRepositoryImpl
import com.example.betteryou.feature.profile.domain.repository.UserProfileRepository
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
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl,
    ): UserProfileRepository
}
