package com.example.betteryou.feature.profile.data.remote.model

data class UserDto (
    val userId:String="",
    val name: String?=null,
    val lastName: String?=null,
    val age: Int? = null,
    val gender: String?=null,
    val height: Double? = null,
    val weight: Double? = null,
    val profilePhotoUrl: String? = null,
    val activityLevel:String?=null,
    val goal:String?=null,

    val dailyCalories:Int?=null,
    val protein:Int?=null,
    val carbs:Int?=null,
    val fats:Int?=null,
    val water:Double?=null,

    val createdAt:Long?=null
)