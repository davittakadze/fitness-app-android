package com.example.betteryou.feature.daily.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.example.betteryou.feature.daily.presentation.DailyScreen
import com.example.betteryou.presentation.navigation.Route

fun NavGraphBuilder.dailyNavGraph(
    navController: NavController
) {
    composable<Route.Daily>(
        deepLinks = listOf(
            navDeepLink { uriPattern = "tbcapp://daily" }
        )
    ) {
        DailyScreen()
    }
}