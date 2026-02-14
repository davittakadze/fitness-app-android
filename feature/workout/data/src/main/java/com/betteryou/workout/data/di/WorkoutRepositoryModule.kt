package com.betteryou.workout.data.di

import com.betteryou.workout.data.repository.WorkoutsRepositoryImpl
import com.betteryou.workout.domain.repository.WorkoutsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkoutRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWorkoutsRepository(
        workoutsRepositoryImpl: WorkoutsRepositoryImpl
    ): WorkoutsRepository
}