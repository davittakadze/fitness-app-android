package com.example.betteryou.feature.daily.domain.model.user

data class User(
    val userId: String = "",
    val name: String? = "",
    val lastName: String? = "",
    val age: Int? = 0,
    val birthDate: String? = "",
    val gender: Gender = Gender.MALE,
    val height: Double? = 0.0,
    val weight: Double? = 0.0,
    val activityLevel: ActivityLevelType = ActivityLevelType.LOW,
    val goal: GoalType = GoalType.LOSE_WEIGHT,
    val profilePhotoUrl: String? = ""
)
