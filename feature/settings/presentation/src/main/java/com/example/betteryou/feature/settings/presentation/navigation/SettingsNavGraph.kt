package com.example.betteryou.feature.settings.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.betteryou.feature.settings.presentation.SettingsScreen
import com.example.betteryou.presentation.navigation.Route

fun NavGraphBuilder.settingsNavGraph(navController: NavController){
    composable<Route.Settings> {
        SettingsScreen(
            onProfileClick = {
                navController.navigate(Route.Profile)
            },
            onNavigateToMenu = {
                navController.navigate(Route.Menu)
            },
            onNavigateToHistory = {
                navController.navigate(Route.History)
            }
        )
    }
}