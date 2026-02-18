package com.betteryou.feature.register.presentation.register.mapper

import com.betteryou.feature.register.domain.model.Gender

fun Gender.toResourceString() : Int {
    return when (this) {
        Gender.MALE -> com.example.betteryou.core_ui.R.string.male
        Gender.FEMALE -> com.example.betteryou.core_ui.R.string.female
        Gender.UNSPECIFIED -> com.example.betteryou.core_ui.R.string.unspecified
    }
}