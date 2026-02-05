package com.example.presentation.login

import com.example.betteryou.presentation.common.UiText

sealed interface LogInSideEffect {
    data object NavigateBack: LogInSideEffect
    data object NavigateHome : LogInSideEffect
    data class ShowError(val error: UiText): LogInSideEffect
}
