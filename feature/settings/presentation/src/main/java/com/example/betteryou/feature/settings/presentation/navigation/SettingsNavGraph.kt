package com.example.betteryou.feature.settings.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.betteryou.feature.settings.presentation.SettingsScreen
import com.example.betteryou.presentation.navigation.HistoryRoute
import com.example.betteryou.presentation.navigation.MenuRoute
import com.example.betteryou.presentation.navigation.ProfileRoute
import com.example.betteryou.presentation.navigation.SettingsRoute

fun NavGraphBuilder.settingsNavGraph(navController: NavController){
    composable<SettingsRoute> {
        SettingsScreen(
            onProfileClick = {
                navController.navigate(ProfileRoute)
            },
            onNavigateToMenu = {
                navController.navigate(MenuRoute)
            },
            onNavigateToHistory = {
                navController.navigate(HistoryRoute)
            }
        )
    }
}