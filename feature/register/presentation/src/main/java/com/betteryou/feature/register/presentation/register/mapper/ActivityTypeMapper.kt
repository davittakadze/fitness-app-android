package com.betteryou.feature.register.presentation.register.mapper

import com.betteryou.feature.register.domain.model.ActivityLevelType
import com.example.betteryou.core_ui.R

fun ActivityLevelType.toResourceString() : Int {
    return when (this) {
        ActivityLevelType.LOW -> R.string.low
        ActivityLevelType.MEDIUM -> R.string.medium
        ActivityLevelType.HIGH -> R.string.high
        ActivityLevelType.VERY_HIGH -> R.string.very_high
    }
}