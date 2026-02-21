package com.example.betteryou.feature.daily.presentation

import javax.inject.Inject

class CalculateNutritionUseCase @Inject constructor() {
    operator fun invoke(bodyData: BodyData) : NutritionResults {
        // 1. BMR
        var bmr = (10 * bodyData.weight) + (6.25 * bodyData.height) - (5 * bodyData.age)
        bmr = if (bodyData.gender == Gender.MALE) bmr + 5 else bmr - 161

        // 2. TDEE
        val activityMultiplier = when(bodyData.activityLevel) {
            ActivityLevelType.LOW -> 1.2
            ActivityLevelType.MEDIUM -> 1.55
            ActivityLevelType.HIGH -> 1.9
            else -> 1.2
        }
        val tdee = (bmr * activityMultiplier).toInt()

        // 3. Goal Adjustment
        val targetCalories = when(bodyData.goal) {
            GoalType.LOSE_WEIGHT -> tdee - 500
            GoalType.GAIN_WEIGHT -> tdee + 500
            else -> tdee
        }

        val water = bodyData.weight * 35

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

/*  val dailyCalories: Int = 0,
    val protein: Int = 0,
    val carbs: Int = 0,
    val fats: Int = 0,
    val water:Double=0.0, */