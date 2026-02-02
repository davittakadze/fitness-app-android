package com.example.homework3.di.repository

import com.example.homework3.data.repository.datastore.DatastoreManagerImpl
import com.example.homework3.data.repository.login.LogInRepositoryImpl
import com.example.homework3.data.repository.register.GoogleSignUpRepositoryImpl
import com.example.homework3.data.repository.register.RegisterRepositoryImpl
import com.example.homework3.data.repository.auth.AuthRepositoryImpl
import com.example.homework3.domain.repository.datastore.DatastoreRepository
import com.example.homework3.domain.repository.login.LogInRepository
import com.example.homework3.domain.repository.register.GoogleSignUpRepository
import com.example.homework3.domain.repository.register.RegisterRepository
import com.example.homework3.domain.repository.token.AuthRepository
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