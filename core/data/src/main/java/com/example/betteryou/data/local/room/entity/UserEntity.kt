package com.example.betteryou.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

//for feature 'profile'

@Entity(tableName = "user_profile")
data class UserEntity(
    @PrimaryKey val id: String,
    val firstName: String?,
    val lastName: String?,
    val age: Int?,
    val gender: String?,
    val height: Float?,
    val weight: Float?,
    val profilePhotoUrl: String?
)