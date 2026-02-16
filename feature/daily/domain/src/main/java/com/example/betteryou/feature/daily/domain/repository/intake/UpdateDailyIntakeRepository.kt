package com.example.betteryou.feature.daily.domain.repository.intake

import com.example.betteryou.feature.daily.domain.model.Intake

interface UpdateDailyIntakeRepository {
    suspend fun updateDailyIntake(
        userId: String,
        intake: Intake,
        date: Long
    )
}