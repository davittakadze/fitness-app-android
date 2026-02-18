package com.betteryou.feature.history.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.betteryou.feature.history.presentation.screen.HistoryScreen
import com.example.betteryou.presentation.navigation.HistoryRoute
import com.example.betteryou.presentation.navigation.WorkoutDetails
import com.example.betteryou.presentation.navigation.WorkoutRoute

fun NavGraphBuilder.historyNavGraph(navController: NavController) {

    composable<HistoryRoute> {
        HistoryScreen(
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
}