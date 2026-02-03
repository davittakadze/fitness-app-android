package com.example.presentation.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.betteryou.presentation.navigation.MainRoute
import com.example.betteryou.presentation.navigation.MenuRoute
import com.example.betteryou.presentation.navigation.SplashRoute
import com.example.presentation.splash.SplashScreen


fun NavGraphBuilder.splashNavGraph(
    navController: NavController,
) {
    composable<SplashRoute> {
        SplashScreen(
            onNavigateMenu = {
                navController.navigate(MenuRoute) {
                    popUpTo(SplashRoute) { inclusive = true }
                }
            },
            onNavigateHome = {
                navController.navigate(MainRoute)
            }
        )
    }
}
