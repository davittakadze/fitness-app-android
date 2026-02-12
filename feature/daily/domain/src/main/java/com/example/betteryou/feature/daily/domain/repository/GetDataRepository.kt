package com.example.betteryou.feature.daily.domain.repository

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.daily.domain.model.User
import kotlinx.coroutines.flow.Flow

interface GetDataRepository {
    fun getUserNutrition(): Flow<Resource<User>>
}