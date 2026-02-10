package com.example.betteryou.feature.profile.data.remote.mapper

import com.example.betteryou.feature.profile.data.remote.model.UserDto
import com.example.betteryou.feature.profile.domain.model.User

fun User.toDto(): UserDto{
    return UserDto(
        firstName = this.firstName,
        lastName = this.lastName,
        age=this.age,
        gender=this.gender,
        height=this.height,
        weight=this.weight,
        photoUrl = this.photoUrl
    )
}