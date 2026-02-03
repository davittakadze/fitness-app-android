package com.example.presentation.splash

sealed interface SplashSideEffect {
    data object NavigateToMenu: SplashSideEffect
    data object NavigateToHome: SplashSideEffect
}
