package com.example.betteryou.repository

import com.example.betteryou.data.repository.datastore.DatastoreManagerImpl
import com.example.betteryou.data.repository.login.LogInRepositoryImpl
import com.example.betteryou.data.repository.register.GoogleSignUpRepositoryImpl
import com.example.betteryou.data.repository.register.RegisterRepositoryImpl
import com.example.betteryou.data.repository.auth.AuthRepositoryImpl
import com.example.betteryou.domain.repository.datastore.DatastoreRepository
import com.example.betteryou.domain.repository.login.LogInRepository
import com.example.betteryou.domain.repository.register.GoogleSignUpRepository
import com.example.betteryou.domain.repository.register.RegisterRepository
import com.example.betteryou.domain.repository.token.AuthRepository
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
    abstract fun bindLogInRepository(
        logInRepositoryImpl: LogInRepositoryImpl
    ): LogInRepository

    @Binds
    @Singleton
    abstract fun bindDatastoreRepository(
        datastoreRepositoryImpl: DatastoreManagerImpl
    ): DatastoreRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindGoogleSignUpRepository(
        googleSignUpRepositoryImpl: GoogleSignUpRepositoryImpl
    ): GoogleSignUpRepository
}