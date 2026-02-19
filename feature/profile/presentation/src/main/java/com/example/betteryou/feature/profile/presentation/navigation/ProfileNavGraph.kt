package com.example.betteryou.feature.profile.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.betteryou.feature.profile.presentation.ProfileScreen
import com.example.betteryou.presentation.navigation.Route

fun NavGraphBuilder.profileNavGraph(navController: NavController){
    composable<Route.Profile> {
        ProfileScreen(
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
}

