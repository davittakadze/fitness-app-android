package com.example.betteryou.feature.recipes.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.betteryou.feature.recipes.presentation.RecipesScreen
import com.example.betteryou.presentation.navigation.RecipesRoute


fun NavGraphBuilder.recipesNavGraph(navController: NavController) {
    composable<RecipesRoute> {
        RecipesScreen()
    }
}