package com.example.homework3.presentation.screen.menu.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.homework3.navigation.MainRoute
import com.example.homework3.presentation.screen.login.navigation.LogInRoute
import com.example.homework3.presentation.screen.menu.MenuScreen
import com.example.homework3.presentation.screen.register.navigation.RegisterRoute
import kotlinx.serialization.Serializable

@Serializable
data object MenuRoute

fun NavGraphBuilder.menuNavGraph(
    navController: NavController
) {
    composable<MenuRoute> {
        MenuScreen(
            onLoginClick = {
                navController.navigate(LogInRoute)
            },
            onRegisterClick = {
                navController.navigate(RegisterRoute)
            },
            onGoogleSignInClick = {
                navController.navigate(MainRoute)
            }
        )
    }
}

