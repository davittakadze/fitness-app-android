package com.betteryou.feature.explore.data.di

import com.betteryou.feature.explore.data.repository.ExerciseRepositoryImpl
import com.betteryou.feature.explore.domain.repository.ExerciseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ExerciseRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindExploreRepository(
        impl: ExerciseRepositoryImpl
    ): ExerciseRepository

}