package com.example.betteryou.presentation.screen.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.betteryou.presentation.navigation.MainRoute
import com.example.betteryou.presentation.screen.splash.SplashScreen
import com.example.presentation.navigation.MenuRoute
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
