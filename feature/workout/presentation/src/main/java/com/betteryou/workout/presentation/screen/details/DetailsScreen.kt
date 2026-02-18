package com.betteryou.workout.presentation.screen.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.betteryou.workout.presentation.screen.details.component.ExerciseDetailCard
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.core_ui.theme.Spacer
import com.example.betteryou.core_ui.components.button.AppButtonType
import com.example.betteryou.core_ui.components.button.TBCAppButton
import com.example.betteryou.presentation.extensions.CollectSideEffects

@Composable
fun DetailsScreen(
    workoutId: String,
    viewModel: DetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.sideEffect.CollectSideEffects {
        when (it) {
            DetailsSideEffect.NavigateBack -> onBackClick()
            is DetailsSideEffect.ShowError -> {

            }
        }
    }

    LaunchedEffect(workoutId) {
        viewModel.onEvent(DetailsEvent.LoadWorkoutExercises(workoutId))
    }

    DetailsContent(
        state = state,
        viewModel = viewModel,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsContent(
    state: DetailsState,
    viewModel: DetailsViewModel,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = state.workoutTitle,
                        style = TBCTheme.typography.headlineMedium,
                        color = TBCTheme.colors.textPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.left_arrow_svgrepo_com),
                            contentDescription = "Back",
                            tint = TBCTheme.colors.textPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TBCTheme.colors.background,
                    titleContentColor = TBCTheme.colors.textPrimary
                )
            )
        },
        bottomBar = {
            Column {
                TBCAppButton(
                    text = "Finish Workout",
                    onClick = { viewModel.onEvent(DetailsEvent.SaveInHistory) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .height(54.dp),
                    type = AppButtonType.Primary,

                    )

                Spacer(modifier = Modifier.height(30.dp))
            }
        },
        containerColor = TBCTheme.colors.background
    ) { padding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = TBCTheme.colors.primary)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(Spacer.spacing16)
            ) {
                items(
                    items = state.exercises,
                    key = { it.exercise.id }
                ) { workoutExercise ->
                    ExerciseDetailCard(
                        workoutExercise = workoutExercise,
                        onAddSet = { exerciseId ->
                            viewModel.onEvent(DetailsEvent.AddSet(workoutExercise.id))
                        },
                        onRemoveSet = { setId ->
                            viewModel.onEvent(DetailsEvent.RemoveSet(setId))
                        },
                        onUpdateSet = { setId, reps, weight ->
                            viewModel.onEvent(DetailsEvent.UpdateSet(setId, reps, weight))
                        }
                    )
                }

                item { Spacer(modifier = Modifier.height(Spacer.spacing16)) }
            }
        }
    }
}




