package com.betteryou.feature.register.domain.model

data class BodyData (
    val weight: Int,
    val height: Int,
    val age: Int,
    val gender: Gender,
    val activityLevel: ActivityLevelType,
    val goal: GoalType
)