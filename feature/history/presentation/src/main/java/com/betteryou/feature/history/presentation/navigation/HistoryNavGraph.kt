package com.betteryou.feature.history.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.betteryou.feature.history.presentation.screen.HistoryScreen
import com.example.betteryou.presentation.navigation.Route

fun NavGraphBuilder.historyNavGraph(navController: NavController) {

    composable<Route.History> {
        HistoryScreen(
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
}