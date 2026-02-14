package com.betteryou.workout.presentation.navgraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.betteryou.workout.presentation.screen.WorkoutScreen
import com.example.betteryou.presentation.navigation.WorkoutRoute

fun NavGraphBuilder.workoutNavGraph(navController: NavController) {
    composable<WorkoutRoute> {
        WorkoutScreen()
    }
}