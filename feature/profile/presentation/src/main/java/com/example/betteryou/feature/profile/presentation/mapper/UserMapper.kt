package com.example.betteryou.feature.profile.presentation.mapper

import com.example.betteryou.feature.domain.model.User
import com.example.betteryou.feature.profile.presentation.model.Sex
import com.example.betteryou.feature.profile.presentation.model.UserUi

fun UserUi.toDomain(): User {
    return User(
        firstName = firstName,
        lastName = lastName,
        age = age,
        gender = gender.toString().lowercase(),
        height = height,
        weight = weight,
        photoUrl = photoUrl
    )
}

fun User.toPresentation(): UserUi {
    return UserUi(
        this.firstName,
        this.lastName,
        this.age,
        if (this.gender==Sex.MALE.toString().lowercase()) {
            Sex.MALE
        } else {
            Sex.FEMALE
        },
        this.height,
        this.weight,
        this.photoUrl
    )
}
