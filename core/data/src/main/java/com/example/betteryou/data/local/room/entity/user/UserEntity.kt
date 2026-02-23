package com.example.betteryou.data.local.room.entity.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val firstName: String?,
    val lastName: String?,
    val age: Int?,
    val birthDate:String?,
    val gender: String?,
    val height: Double?,
    val weight: Double?,
    val profilePhotoUrl: String?,
    val activityLevel: String?,
    val goal: String?
)