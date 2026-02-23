package com.example.betteryou.feature.profile.data.remote.mapper

import com.example.betteryou.data.local.room.entity.user.UserEntity
import com.example.betteryou.feature.profile.data.remote.model.UserDto
import com.example.betteryou.feature.profile.domain.model.User

fun UserEntity.toDomain(): User =User(
    this.firstName,
    this.lastName,
    this.age,
    birthDate=birthDate,
    this.gender,
    this.height,
    this.weight,
    this.profilePhotoUrl
)
