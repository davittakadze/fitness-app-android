package com.example.presentation.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.betteryou.presentation.navigation.Route
import com.example.presentation.login.LogInScreen

fun NavGraphBuilder.logInNavGraph(
    navController: NavController
) {
    composable<Route.LogIn> {
        LogInScreen(
            onBackClick = {
                navController.popBackStack()
            },
            onLogInClick = {
                navController.navigate(Route.Main)
            }
        )
    }
}
