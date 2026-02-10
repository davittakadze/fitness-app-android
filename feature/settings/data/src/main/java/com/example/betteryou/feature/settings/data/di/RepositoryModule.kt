package com.example.betteryou.feature.settings.data.di

import com.example.betteryou.feature.settings.data.repository.delete_account.DeleteAccountRepositoryImpl
import com.example.betteryou.feature.settings.data.repository.logout.LogoutRepositoryImpl
import com.example.betteryou.feature.settings.domain.repository.delete_account.DeleteAccountRepository
import com.example.betteryou.feature.settings.domain.repository.logout.LogoutRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class  RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindLogoutRepository(
        authRepositoryImpl: LogoutRepositoryImpl
    ): LogoutRepository

    @Binds
    @Singleton
    abstract fun bindDeleteAccountRepository(
        authRepositoryImpl: DeleteAccountRepositoryImpl
    ): DeleteAccountRepository

}