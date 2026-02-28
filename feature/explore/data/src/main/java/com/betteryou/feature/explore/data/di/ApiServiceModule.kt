package com.betteryou.feature.explore.data.di

import com.betteryou.feature.explore.data.api_service.GetExercisesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiServiceModule {
    @Provides
    @Singleton
    fun provideGetExercisesApiService(retrofit: Retrofit): GetExercisesApiService {
        return retrofit.create(GetExercisesApiService::class.java)
    }
}