package com.example.betteryou.feature.daily.domain.usecase

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.daily.domain.model.User
import com.example.betteryou.feature.daily.domain.repository.GetDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDataUseCase @Inject constructor(
    private val repository: GetDataRepository,
) {
    operator fun invoke(): Flow<Resource<User>> {
        return repository.getUserNutrition()
    }
}