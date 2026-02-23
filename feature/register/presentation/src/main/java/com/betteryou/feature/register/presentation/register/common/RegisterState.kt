package com.betteryou.feature.register.presentation.register.common

import com.betteryou.feature.register.domain.model.ActivityLevelType
import com.betteryou.feature.register.domain.model.Gender
import com.betteryou.feature.register.domain.model.GoalType
import com.example.betteryou.presentation.navigation.RegistrationRoute
import java.time.LocalDate

data class RegisterState(
    val currentStep: RegistrationRoute = RegistrationRoute.ActivityLevel,
    val activityLevel: ActivityLevelType? = null,
    val goal: GoalType? = null,
    val error: String? = null,

    val gender: Gender = Gender.UNSPECIFIED,
    val email: String = "",
    val name: String = "",
    val lastName:String="",
    val age: String = "",
    val weight: String = "",
    val height: String = "",
    val password: String = "",
    val password2: String = "",
    val isPassword1Visible: Boolean = false,
    val isPassword2Visible: Boolean = false,
    val loader: Boolean = false,
)