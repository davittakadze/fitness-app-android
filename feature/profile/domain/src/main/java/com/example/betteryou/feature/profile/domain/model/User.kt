package com.example.betteryou.feature.profile.domain.model


data class User (
    val firstName: String?=null,
    val lastName: String?=null,
    val age: Int? = null,
    val gender: String?=null,
    val height: Float? = null,
    val weight: Float? = null,
    val photoUrl: String? = null
)