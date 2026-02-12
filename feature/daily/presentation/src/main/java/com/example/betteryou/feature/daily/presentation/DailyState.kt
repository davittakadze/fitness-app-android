package com.example.betteryou.feature.daily.presentation

data class DailyState(
    //calories
    val consumedCalories: Int = 0,
    val totalCaloriesGoal:Int = 1200,

    //proteins
    val protein:Int = 0,
    val totalProteinGoal: Int= 100,

    //fats
    val fat: Int= 0,
    val totalFatGoal: Int = 100,

    //carbs
    val carbs: Int = 0,
    val totalCarbsGoal: Int= 100,

    //water
    val currentWater: Float = 0f,
    val waterGoal: Float = 3f,
)
