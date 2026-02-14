package com.betteryou.workout.data.di

import com.betteryou.workout.data.remote.api_service.GetWorkoutsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {
    @Provides
    @Singleton
    fun provideGetWorkoutsApiService(retrofit: Retrofit): GetWorkoutsApiService {
        return retrofit.create(GetWorkoutsApiService::class.java)
    }
}