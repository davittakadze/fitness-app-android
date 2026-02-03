package com.example.betteryou.presentation.screen.register.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.betteryou.presentation.screen.register.RegisterScreen
import com.example.presentation.login.navigation.LogInRoute
import kotlinx.serialization.Serializable

@Serializable
data object RegisterRoute

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
