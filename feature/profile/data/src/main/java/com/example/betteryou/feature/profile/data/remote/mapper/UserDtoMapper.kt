package com.example.betteryou.feature.profile.data.remote.mapper

import com.example.betteryou.data.local.room.entity.UserEntity
import com.example.betteryou.feature.profile.data.remote.model.UserDto

fun UserDto.toEntity(): UserEntity{
    return UserEntity(
        this.id,
        this.firstName,
        this.lastName,
        this.age,
        this.gender,
        this.height,
        this.weight,
        profilePhotoUrl = this.photoUrl
    )
}

fun UserEntity.toDto(): UserDto = UserDto(
    id = this.id,
    firstName = this.firstName,
    lastName = this.lastName,
    age = this.age,
    gender = this.gender,
    height = this.height,
    weight = this.weight,
    photoUrl = this.profilePhotoUrl
)


