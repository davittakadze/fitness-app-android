package com.example.homework3.presentation.screen.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.homework3.navigation.MainRoute
import com.example.homework3.presentation.screen.menu.navigation.MenuRoute
import com.example.homework3.presentation.screen.splash.SplashScreen
import kotlinx.serialization.Serializable

@Serializable
data object SplashRoute

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
