package com.example.betteryou.feature.domain.model

import android.net.Uri

data class User (
    val firstName: String?=null,
    val lastName: String?=null,
    val age: Int? = null,
    val gender: String?=null,
    val height: Float? = null,
    val weight: Float? = null,
    val photoUrl: Uri? = null
)