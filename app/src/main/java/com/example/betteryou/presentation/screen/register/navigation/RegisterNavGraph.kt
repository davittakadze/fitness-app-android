package com.example.betteryou.presentation.screen.register.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.betteryou.presentation.navigation.LogInRoute
import com.example.betteryou.presentation.navigation.RegisterRoute
import com.example.betteryou.presentation.screen.register.RegisterScreen

fun NavGraphBuilder.registerNavGraph(
    navController: NavController
) {
    composable<RegisterRoute> {
        RegisterScreen(
            onRegisterClick = {
                navController.navigate(LogInRoute) {
                    popUpTo(RegisterRoute) { inclusive = true }
                }
            },
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
}
