package com.example.homework3.presentation.screen.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.homework3.presentation.screen.profile.ProfileScreen
import kotlinx.serialization.Serializable

@Serializable
data object ProfileRoute

fun NavGraphBuilder.profileNavGraph(navController: NavController){
    composable<ProfileRoute> {
        ProfileScreen()
    }
}