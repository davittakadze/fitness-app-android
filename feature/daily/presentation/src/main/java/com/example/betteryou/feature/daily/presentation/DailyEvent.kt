package com.example.betteryou.feature.daily.presentation

sealed interface DailyEvent {
    data class ChangeWater(val water: Float) : DailyEvent
    data class SaveWaterToDb(val water:Float) : DailyEvent
    data object LoadUserNutrition : DailyEvent
    data class ChangePage(val page:Int): DailyEvent
}
