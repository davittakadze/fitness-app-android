package com.example.betteryou.feature.daily.domain.repository.data

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.daily.domain.model.Nutrient
import kotlinx.coroutines.flow.Flow

interface GetNutrientsRepository {
    fun getUserNutrition(): Flow<Resource<Nutrient>>
}