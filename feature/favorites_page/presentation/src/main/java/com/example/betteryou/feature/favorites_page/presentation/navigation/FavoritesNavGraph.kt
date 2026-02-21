package com.example.betteryou.feature.favorites_page.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.betteryou.feature.favorites_page.presentation.FavoritesScreen
import com.example.betteryou.presentation.navigation.Route

fun NavGraphBuilder.favoritesNavGraph(navController: NavController) {
    composable<Route.Favorites> {
        FavoritesScreen(
            onNavigateBack = {
                navController.popBackStack()
            }
        )
    }
}