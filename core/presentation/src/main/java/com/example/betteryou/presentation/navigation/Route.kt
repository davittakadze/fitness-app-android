package com.example.betteryou.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable data object Main : Route
    @Serializable data object Splash : Route
    @Serializable data object LogIn : Route
    @Serializable data object Menu : Route
    @Serializable data object Profile : Route
    @Serializable data object Settings : Route
    @Serializable data object Daily : Route
    @Serializable data object Workout : Route
    @Serializable data object History : Route
    @Serializable data class WorkoutDetails(val workoutId: String)
    @Serializable data object Recipes : Route
    @Serializable data object Explore : Route
    @Serializable data class ExploreDetails(val workoutId: String) : Route
}