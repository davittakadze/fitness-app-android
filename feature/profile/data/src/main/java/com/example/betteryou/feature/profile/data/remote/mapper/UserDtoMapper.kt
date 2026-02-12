package com.example.betteryou.feature.profile.data.remote.mapper

import com.example.betteryou.data.local.room.entity.UserEntity
import com.example.betteryou.feature.profile.data.remote.model.UserDto
import com.example.betteryou.feature.profile.domain.model.User

fun UserDto.toEntity(): UserEntity{
    return UserEntity(
        this.userId,
        this.name,
        this.lastName,
        this.age,
        this.gender,
        this.height,
        this.weight,
        profilePhotoUrl = this.profilePhotoUrl
    )
}

fun UserEntity.toDomain(): User =User(
    this.firstName,
    this.lastName,
    this.age,
    this.gender,
    this.height,
    this.weight,
    this.profilePhotoUrl
)

fun UserEntity.toDto(): UserDto = UserDto(
    userId = this.id,
    name = this.firstName,
    lastName = this.lastName,
    age = this.age,
    gender = this.gender,
    height = this.height,
    weight = this.weight,
    profilePhotoUrl = this.profilePhotoUrl
)

fun UserDto.toDomain(): User{
    return User(
        this.name,
        this.lastName,
        this.age,
        this.gender,
        this.height,
        this.weight,
        photoUrl =  this.profilePhotoUrl
    )
}

