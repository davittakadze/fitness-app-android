package com.example.betteryou.feature.daily.domain.repository.intake

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.daily.domain.model.Intake
import kotlinx.coroutines.flow.Flow

interface GetDailyIntakeRepository {
    fun getDailyIntake(userId: String, date: Long): Flow<Resource<Intake>>
}