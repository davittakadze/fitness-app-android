package com.betteryou.feature.register.presentation.register.common

import com.betteryou.feature.register.domain.model.ActivityLevelType
import com.betteryou.feature.register.domain.model.Gender
import com.betteryou.feature.register.domain.model.GoalType

sealed interface RegisterEvent {
    data object OnBackClick : RegisterEvent

    // Screen 1: Activity Level
    data class OnActivityLevelChange(val level: ActivityLevelType) : RegisterEvent

    // Screen 2: Basic Info
    data class OnGenderChange(val gender: Gender) : RegisterEvent
    data class OnAgeChange(val age: String) : RegisterEvent
    data class OnNameChange(val name: String) : RegisterEvent
    data class OnLastNameChange(val lastName: String) : RegisterEvent

    // Screen 3: Body Metrics
    data class OnWeightChange(val weight: String) : RegisterEvent
    data class OnHeightChange(val height: String) : RegisterEvent

    // Screen 4: Goal Setting
    data class OnGoalTypeChange(val goalType: GoalType) : RegisterEvent

    // Screen 5: Account Creation (Final)
    data class OnEmailChange(val email: String) : RegisterEvent
    data class OnPasswordChange(val password: String) : RegisterEvent
    data class OnRepeatPasswordChange(val password: String) : RegisterEvent
    data class OnTogglePassword1Visibility(val isVisible: Boolean) : RegisterEvent
    data class OnTogglePassword2Visibility(val isVisible: Boolean) : RegisterEvent
    data object OnRegisterButtonClick : RegisterEvent
}