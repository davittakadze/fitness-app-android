package com.betteryou.feature.register.domain.model

data class RegisterUserInfo(
    val userId: String = "",
    val name: String = "",
    val lastName:String?=null,
    val age: Int=0,
    val gender: String = "",
    val height: Double = 0.0,
    val weight: Double = 0.0,
    val profilePhotoUrl:String?=null,
    val activityLevel: String = "",
    val goal: String = "",

    val dailyCalories: Int = 0,
    val protein: Int = 0,
    val carbs: Int = 0,
    val fats: Int = 0,
    val water:Double=0.0,

    val createdAt: Long = System.currentTimeMillis()
)