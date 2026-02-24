package com.example.betteryou.feature.settings.presentation

data class SettingsState (
    val isLoading:Boolean=false,
    val isDarkThemeEnabled: Boolean = false,
    val isGeorgianLanguageEnabled:Boolean=false,

    val password:String="",
    val isBottomSheetOpen:Boolean=false,
    val isPasswordVisible:Boolean=false
)