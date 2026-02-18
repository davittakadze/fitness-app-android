package com.betteryou.feature.register.presentation.register.mapper

import com.betteryou.feature.register.domain.model.GoalType
import com.example.betteryou.core_ui.R

fun GoalType.toResourceString() : Int {
    return when (this) {
        GoalType.LOSE_WEIGHT -> R.string.lose_weight
        GoalType.KEEP_FIT -> R.string.keep_fit
        GoalType.GAIN_WEIGHT -> R.string.gain_weight
    }
}