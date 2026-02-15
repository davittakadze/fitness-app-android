package com.example.betteryou.feature.daily.data.di.repository

import com.example.betteryou.feature.daily.data.repository.intake.GetDailyIntakeRepositoryImpl
import com.example.betteryou.feature.daily.data.repository.intake.UpdateDailyIntakeRepositoryImpl
import com.example.betteryou.feature.daily.data.repository.nutrient.GetNutrientsRepositoryImpl
import com.example.betteryou.feature.daily.data.repository.product.ProductRepositoryImpl
import com.example.betteryou.feature.daily.domain.repository.data.GetNutrientsRepository
import com.example.betteryou.feature.daily.domain.repository.intake.GetDailyIntakeRepository
import com.example.betteryou.feature.daily.domain.repository.intake.UpdateDailyIntakeRepository
import com.example.betteryou.feature.daily.domain.repository.product.ProductRepository
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

   @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl,
    ): ProductRepository
}