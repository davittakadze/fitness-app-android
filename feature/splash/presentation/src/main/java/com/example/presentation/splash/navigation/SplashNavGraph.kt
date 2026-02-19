package com.example.presentation.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.betteryou.presentation.navigation.Route
import com.example.presentation.splash.SplashScreen


fun NavGraphBuilder.splashNavGraph(
    navController: NavController,
) {
    composable<Route.Splash> {
        SplashScreen(
            onNavigateMenu = {
                navController.navigate(Route.Menu) {
                    popUpTo(Route.Splash) { inclusive = true }
                }
            },
            onNavigateHome = {
                navController.navigate(Route.Main)
            }
        )
    }
}
