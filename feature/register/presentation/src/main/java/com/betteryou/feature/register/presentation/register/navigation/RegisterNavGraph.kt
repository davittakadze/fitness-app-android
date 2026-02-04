package com.betteryou.feature.register.presentation.register.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.betteryou.presentation.navigation.LogInRoute
import com.example.betteryou.presentation.navigation.RegisterRoute
import com.betteryou.feature.register.presentation.register.RegisterScreen

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
