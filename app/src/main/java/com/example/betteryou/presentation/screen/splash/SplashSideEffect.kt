package com.example.betteryou.presentation.screen.splash

sealed interface SplashSideEffect {
    data object NavigateToMenu: SplashSideEffect
    data object NavigateToHome: SplashSideEffect
}
