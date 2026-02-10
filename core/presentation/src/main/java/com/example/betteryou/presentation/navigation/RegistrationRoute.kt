package com.example.betteryou.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface RegistrationRoute {
    @Serializable data object Graph : RegistrationRoute
    @Serializable data object ActivityLevel : RegistrationRoute
    @Serializable data object BasicInfo : RegistrationRoute
    @Serializable data object BodyMetrics : RegistrationRoute
    @Serializable data object GoalSetting : RegistrationRoute
    @Serializable data object AccountCreation : RegistrationRoute
}