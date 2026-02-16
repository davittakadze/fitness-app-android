package com.example.betteryou.feature.daily.domain.usecase.data

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.daily.domain.model.Nutrient
import com.example.betteryou.feature.daily.domain.repository.data.GetNutrientsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNutrientsUseCase @Inject constructor(
    private val repository: GetNutrientsRepository,
) {
    operator fun invoke(): Flow<Resource<Nutrient>> {
        return repository.getUserNutrition()
    }
}