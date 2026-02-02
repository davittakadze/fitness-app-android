package com.example.homework3.presentation.screen.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.homework3.navigation.MainRoute
import com.example.homework3.presentation.screen.login.LogInScreen
import com.example.homework3.presentation.screen.menu.navigation.MenuRoute
import kotlinx.serialization.Serializable

@Serializable
data object LogInRoute

fun NavGraphBuilder.logInNavGraph(
    navController: NavController
) {
    composable<LogInRoute> {
        LogInScreen(
            onBackClick = {
                navController.popBackStack()
            },
            onLogInClick = {
                navController.navigate(MainRoute)
            }
        )
    }
}
