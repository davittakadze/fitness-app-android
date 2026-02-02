package com.example.homework3.presentation.screen.menu

sealed interface MenuSideEffect {
    data object NavigateToLogInPage: MenuSideEffect
    data object NavigateToRegisterPage: MenuSideEffect
    data class ShowError(val error: String): MenuSideEffect
    data object NavigateToHome: MenuSideEffect
}
