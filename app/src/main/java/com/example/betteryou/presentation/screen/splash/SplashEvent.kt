package com.example.betteryou.presentation.screen.splash

sealed interface SplashEvent {
    data object OnStartSplash: SplashEvent
    data object OnStopSplash:SplashEvent
}
