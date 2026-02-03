package com.example.presentation

sealed interface MenuSideEffect {
    data object NavigateToLogInPage: MenuSideEffect
    data object NavigateToRegisterPage: MenuSideEffect
    data class ShowError(val error: String): MenuSideEffect
    data object NavigateToHome: MenuSideEffect

    data object RequestGoogleSignIn: MenuSideEffect
}
