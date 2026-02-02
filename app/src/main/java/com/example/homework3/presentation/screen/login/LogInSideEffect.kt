package com.example.homework3.presentation.screen.login

sealed interface LogInSideEffect {
    data object NavigateBack: LogInSideEffect
    data object NavigateHome : LogInSideEffect
    data class ShowError(val error:String): LogInSideEffect
}
