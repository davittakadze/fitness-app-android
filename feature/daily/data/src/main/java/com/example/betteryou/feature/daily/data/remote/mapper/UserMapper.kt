package com.example.betteryou.feature.daily.data.remote.mapper

import com.example.betteryou.data.local.room.entity.user.UserEntity
import com.example.betteryou.feature.daily.data.remote.model.user.UserDto
import com.example.betteryou.feature.daily.domain.model.user.ActivityLevelType
import com.example.betteryou.feature.daily.domain.model.user.Gender
import com.example.betteryou.feature.daily.domain.model.user.GoalType
import com.example.betteryou.feature.daily.domain.model.user.User



fun UserDto.toEntity(): UserEntity {
    return UserEntity(
        id = userId,
        firstName = name,
        lastName = lastName,
        age = age,
        gender = gender,
        height = height,
        weight = weight,
        activityLevel = activityLevel,
        goal = goal,
        profilePhotoUrl = profilePhotoUrl,
        birthDate = birthDate
    )
}


fun UserEntity.toDomain(): User{
    return User(
        id,
        firstName,
        lastName,
        age,
        birthDate=birthDate,
        gender = gender?.uppercase()?.let { Gender.valueOf(it) } ?: Gender.MALE,
        height,
        weight,
        activityLevel = activityLevel?.let { ActivityLevelType.valueOf(it) } ?: ActivityLevelType.LOW,
        goal = goal?.let { GoalType.valueOf(it) } ?: GoalType.LOSE_WEIGHT,
        profilePhotoUrl=profilePhotoUrl
    )
}
