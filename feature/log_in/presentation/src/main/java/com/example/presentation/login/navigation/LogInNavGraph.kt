package com.example.presentation.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.betteryou.presentation.navigation.MainRoute
import com.example.presentation.login.LogInScreen
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
