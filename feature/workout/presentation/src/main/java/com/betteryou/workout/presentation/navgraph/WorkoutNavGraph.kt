package com.betteryou.workout.presentation.navgraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.betteryou.workout.presentation.screen.details.DetailsScreen
import com.betteryou.workout.presentation.screen.workout.WorkoutScreen
import com.example.betteryou.presentation.navigation.WorkoutDetails
import com.example.betteryou.presentation.navigation.WorkoutRoute

fun NavGraphBuilder.workoutNavGraph(navController: NavController) {
    composable<WorkoutRoute> {
        WorkoutScreen(
            onNavigate = { workoutId ->
                navController.navigate(WorkoutDetails(workoutId = workoutId))
            }
        )
    }

    composable<WorkoutDetails> { backStackEntry ->
        val details: WorkoutDetails = backStackEntry.toRoute()
        DetailsScreen(
            workoutId = details.workoutId,
            onBackClick = { navController.popBackStack() }
        )
    }
}