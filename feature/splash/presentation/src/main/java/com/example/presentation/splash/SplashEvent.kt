package com.example.presentation.splash

sealed interface SplashEvent {
    data object OnStartSplash: SplashEvent
    data object OnStopSplash:SplashEvent
}
