package com.example.betteryou.feature.daily.data.repository.intake

import com.example.betteryou.data.local.room.dao.intake.DailyIntakeDao
import com.example.betteryou.feature.daily.domain.model.Intake
import com.example.betteryou.feature.daily.domain.repository.intake.UpdateDailyIntakeRepository
import javax.inject.Inject

class UpdateDailyIntakeRepositoryImpl @Inject constructor(
    private val dailyIntakeDao: DailyIntakeDao
) : UpdateDailyIntakeRepository {

    override suspend fun updateDailyIntake(
        userId: String,
        intake: Intake,
        date: Long
    ) {
        dailyIntakeDao.updateOrInsertDailyIntake(
            userId = userId,
            date = date,
            calories = intake.dailyCalories,
            protein = intake.protein,
            fat = intake.fats,
            carbs = intake.carbs,
            water = intake.water
        )
    }
}