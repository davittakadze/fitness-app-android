package com.example.betteryou.feature.daily.domain.usecase.user

import com.example.betteryou.feature.daily.domain.model.user.ActivityLevelType
import com.example.betteryou.feature.daily.domain.model.user.Gender
import com.example.betteryou.feature.daily.domain.model.user.GoalType
import com.example.betteryou.feature.daily.domain.model.user.NutritionResults
import com.example.betteryou.feature.daily.domain.model.user.User
import javax.inject.Inject

class CalculateNutritionUseCase @Inject constructor() {
    operator fun invoke(user: User) : NutritionResults {
        // 1. BMR
        var  bmr = (10 * (user.weight ?: 0.0)) +
                (6.25 * (user.height ?: 0.0)) -
                (5 * (user.age ?: 0))
        bmr = if (user.gender == Gender.MALE) bmr + 5 else bmr - 161

        // 2. TDEE
        val activityMultiplier = when(user.activityLevel) {
            ActivityLevelType.LOW -> 1.2
            ActivityLevelType.MEDIUM -> 1.55
            ActivityLevelType.HIGH -> 1.9
            else -> 1.2
        }
        val tdee = (bmr * activityMultiplier).toInt()

        // 3. Goal Adjustment
        val targetCalories = when(user.goal) {
            GoalType.LOSE_WEIGHT -> tdee - 500
            GoalType.GAIN_WEIGHT -> tdee + 500
            else -> tdee
        }

        val water = (user.weight ?: 0.0) * 35

        // 4. Macros (30% protein, 40% carbs, 30% fat)
        return NutritionResults(
            calories = targetCalories,
            protein = (targetCalories * 0.3 / 4).toInt(),
            carbs = (targetCalories * 0.4 / 4).toInt(),
            fats = (targetCalories * 0.3 / 9).toInt(),
            waterLiters = (water / 1000.0)
        )
    }
}