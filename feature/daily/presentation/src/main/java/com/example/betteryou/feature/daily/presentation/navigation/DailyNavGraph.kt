package com.example.betteryou.feature.daily.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.betteryou.feature.daily.presentation.DailyScreen
import com.example.betteryou.presentation.navigation.DailyRoute

fun NavGraphBuilder.dailyNavGraph(
    navController: NavController
) {
    composable<DailyRoute> {
        DailyScreen()
    }
}