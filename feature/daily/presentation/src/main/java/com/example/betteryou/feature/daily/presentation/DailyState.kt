package com.example.betteryou.feature.daily.presentation

data class DailyState(
    //calories
    val consumedCalories: Int=0,
    val totalCaloriesGoal:Int=0,

    //proteins
    val protein:Int=0,
    val totalProteinGoal: Int=0,

    //fats
    val fat: Int=0,
    val totalFatGoal: Int=0,

    //carbs
    val carbs: Int=0,
    val totalCarbsGoal: Int=0,

    //water
    val currentWater: Float=0f,
    val waterGoal: Double=0.0,

    //pager
    val currentPage: Int = 0
)
