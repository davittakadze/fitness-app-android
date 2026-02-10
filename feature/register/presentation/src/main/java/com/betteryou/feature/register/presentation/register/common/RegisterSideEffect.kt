package com.betteryou.feature.register.presentation.register.common

import com.example.betteryou.presentation.common.UiText

sealed interface RegisterSideEffect {
    data object NavigateBack: RegisterSideEffect
    data object NavigateToHome: RegisterSideEffect
    data class ShowError(val error: UiText): RegisterSideEffect
}
