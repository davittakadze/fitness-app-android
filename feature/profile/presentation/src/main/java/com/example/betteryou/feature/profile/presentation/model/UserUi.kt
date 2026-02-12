package com.example.betteryou.feature.profile.presentation.model

import android.net.Uri


data class UserUi(
    val firstName: String?=null,
    val lastName: String?=null,
    val age: Int? = null,
    val gender: Sex?=null,
    val height: Double? = null,
    val weight: Double? = null,
    val photoUrl: Uri? = null
)