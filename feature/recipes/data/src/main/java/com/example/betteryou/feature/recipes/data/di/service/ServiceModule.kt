package com.example.betteryou.feature.recipes.data.di.service

import com.example.betteryou.feature.recipes.data.remote.service.RecipeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideMealService(retrofit: Retrofit): RecipeService {
        return retrofit.create(RecipeService::class.java)
    }
}