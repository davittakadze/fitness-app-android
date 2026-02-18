package com.example.betteryou.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object MainRoute

@Serializable
data object SplashRoute

@Serializable
data object LogInRoute

@Serializable
data object MenuRoute

@Serializable
data object ProfileRoute

@Serializable
data object SettingsRoute

@Serializable
data object DailyRoute

@Serializable
data object WorkoutRoute

@Serializable
data object HistoryRoute

@Serializable
data class WorkoutDetails(val workoutId: String)

@Serializable
data object RecipesRoute