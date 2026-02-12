package com.example.betteryou.feature.daily.data.di

import com.example.betteryou.feature.daily.data.repository.GetDailyIntakeRepositoryImpl
import com.example.betteryou.feature.daily.data.repository.GetNutrientsRepositoryImpl
import com.example.betteryou.feature.daily.data.repository.UpdateDailyIntakeRepositoryImpl
import com.example.betteryou.feature.daily.domain.repository.intake.GetDailyIntakeRepository
import com.example.betteryou.feature.daily.domain.repository.data.GetNutrientsRepository
import com.example.betteryou.feature.daily.domain.repository.intake.UpdateDailyIntakeRepository
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
    abstract fun bindGetDataRepository(
        getDataRepositoryImpl: GetNutrientsRepositoryImpl,
    ): GetNutrientsRepository

    @Binds
    @Singleton
    abstract fun bindGetDailyIntakeRepository(
        getDailyIntakeRepositoryImpl: GetDailyIntakeRepositoryImpl,
    ): GetDailyIntakeRepository

    @Binds
    @Singleton
    abstract fun bindUpdateDailyIntakeRepository(
        updateDailyIntakeRepositoryImpl: UpdateDailyIntakeRepositoryImpl,
    ): UpdateDailyIntakeRepository
}