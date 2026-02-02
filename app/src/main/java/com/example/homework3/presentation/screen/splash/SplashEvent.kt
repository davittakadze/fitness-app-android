package com.example.homework3.presentation.screen.splash

sealed interface SplashEvent {
    data object OnStartSplash: SplashEvent
    data object OnStopSplash:SplashEvent
}
