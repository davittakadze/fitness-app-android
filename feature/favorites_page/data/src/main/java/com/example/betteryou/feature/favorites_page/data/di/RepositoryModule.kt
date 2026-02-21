package com.example.betteryou.feature.favorites_page.data.di

import com.example.betteryou.feature.favorites_page.data.repository.FavoritesPageRepositoryImpl
import com.example.betteryou.feature.favorites_page.domain.repository.FavoritesPageRepository
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
    abstract fun bindRepository(
        repositoryImpl: FavoritesPageRepositoryImpl,
    ): FavoritesPageRepository
}