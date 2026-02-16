package com.example.betteryou.feature.daily.domain.usecase.intake

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.daily.domain.model.Intake
import com.example.betteryou.feature.daily.domain.repository.intake.GetDailyIntakeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDailyIntakeUseCase @Inject constructor(
    private val repository: GetDailyIntakeRepository
) {
    operator fun invoke(userId: String, date: Long): Flow<Resource<Intake>> {
        return repository.getDailyIntake(userId, date)
    }
}