package com.example.betteryou.feature.daily.domain.usecase.intake

import com.example.betteryou.feature.daily.domain.model.intake.Intake
import com.example.betteryou.feature.daily.domain.repository.intake.UpdateDailyIntakeRepository
import javax.inject.Inject

class UpdateDailyIntakeUseCase @Inject constructor(
    private val repository: UpdateDailyIntakeRepository
) {
    suspend operator fun invoke(
        userId: String,
        intake: Intake,
        date: Long
    ) {
        repository.updateDailyIntake(userId, intake, date)
    }
}