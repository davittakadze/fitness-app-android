package com.example.betteryou.feature.daily.presentation

sealed interface DailyEvent {
    //water events
    data class ChangeWater(val water: Float) : DailyEvent
    data object LoadUserNutrition : DailyEvent
}