package com.example.betteryou.feature.profile.presentation.mapper

import androidx.core.net.toUri
import com.example.betteryou.feature.profile.domain.model.User
import com.example.betteryou.feature.profile.presentation.model.Sex
import com.example.betteryou.feature.profile.presentation.model.UserUi

fun UserUi.toDomain(): User {
    return User(
        firstName = firstName,
        lastName = lastName,
        age = age,
        birthDate=birthDate,
        gender = gender.toString().lowercase(),
        height = height,
        weight = weight,
        photoUrl = photoUrl?.toString()
    )
}

fun User.toPresentation(): UserUi {
    return UserUi(
        firstName = this.firstName,
        lastName=this.lastName,
        age=this.age,
        birthDate=this.birthDate,
        gender=if (this.gender==Sex.MALE.toString().lowercase()) {
            Sex.MALE
        } else {
            Sex.FEMALE
        },
        height=this.height,
        weight=this.weight,
        photoUrl=this.photoUrl?.toUri()
    )
}
