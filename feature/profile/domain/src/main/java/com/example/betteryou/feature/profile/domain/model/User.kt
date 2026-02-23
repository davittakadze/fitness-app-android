package com.example.betteryou.feature.profile.domain.model


data class User (
    val firstName: String?=null,
    val lastName: String?=null,
    val age: Int? = null,
    val birthDate:String?=null,
    val gender: String?=null,
    val height: Double? = null,
    val weight: Double? = null,
    val photoUrl: String? = null
)