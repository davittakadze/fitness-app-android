package com.bettetyou.feature.notification.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bettetyou.feature.notification.presentation.screen.NotificationScreen
import com.example.betteryou.presentation.navigation.Route

fun NavGraphBuilder.notificationNavGraph(navController: NavController) {

    composable<Route.Notifications> {
        NotificationScreen(
            onNavigateBack = {
                navController.popBackStack()
            },
            onNavigateToDaily = {
                navController.navigate(Route.Daily)
            }
        )
    }
}