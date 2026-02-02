package com.example.homework3.presentation.screen.splash

sealed interface SplashSideEffect {
    data object NavigateToMenu: SplashSideEffect
    data object NavigateToHome: SplashSideEffect
}
