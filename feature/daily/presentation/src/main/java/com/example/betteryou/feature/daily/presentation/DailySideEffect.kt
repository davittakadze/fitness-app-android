package com.example.betteryou.feature.daily.presentation

sealed interface DailySideEffect {
    data class ShowError(val error:String): DailySideEffect
}