package com.betteryou.workout.presentation.screen.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.betteryou.workout.presentation.screen.workout.component.BetterYouBottomSheet
import com.betteryou.workout.presentation.screen.workout.component.ExerciseSelectionRow
import com.betteryou.workout.presentation.screen.workout.component.MyWorkoutsListScreen
import com.betteryou.workout.presentation.mapper.toPresentation
import com.bettetyou.core.model.Workout
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.core_ui.components.button.AppButtonType
import com.example.betteryou.core_ui.components.button.TBCAppButton
import com.example.betteryou.core_ui.components.text_field.TBCAppSearchField
import com.example.betteryou.core_ui.components.text_field.TBCAppTextField
import com.example.betteryou.presentation.extensions.CollectSideEffects
import com.example.betteryou.presentation.snackbar.SnackBarController
import com.example.betteryou.presentation.snackbar.SnackbarEvent

@Composable
fun WorkoutScreen(
    viewModel: WorkoutViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    viewModel.sideEffect.CollectSideEffects { effect ->
        when (effect) {
            is WorkoutSideEffect.ShowError -> {
                viewModel.onEvent(WorkoutEvent.DismissSheet)
                SnackBarController.sendEvent(
                    SnackbarEvent(
                        message = effect.message.asString(context)
                    )
                )
            }

            is WorkoutSideEffect.NavigateToDetails -> {
                onNavigate.invoke(effect.workoutId)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(WorkoutEvent.ObserveWorkouts)
    }

    WorkoutContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WorkoutContent(
    state: WorkoutState,
    onEvent: (WorkoutEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.workouts),
                        style = TBCTheme.typography.headlineLarge,
                        color = TBCTheme.colors.textPrimary
                    )
                },
                actions = {
                    IconButton(onClick = { onEvent(WorkoutEvent.ShowSheet) }) {
                        Icon(
                            painter = painterResource(R.drawable.plus_svgrepo_com_2),
                            contentDescription = "Add",
                            tint = TBCTheme.colors.textPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = TBCTheme.colors.background,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        if (state.isSheetOpen) {
            CreateWorkoutBottomSheet(
                state = state,
                onDismiss = { onEvent(WorkoutEvent.DismissSheet) },
                onEvent = onEvent,
            )
        }

        Box(
            modifier = Modifier
                .background(TBCTheme.colors.background)
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column {

                MyWorkoutsListScreen(
                    workouts = state.myWorkouts.map(Workout::toPresentation),
                    onWorkoutClick = {
                        onEvent(WorkoutEvent.NavigateToDetails(it))
                    },
                    onEvent = onEvent
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateWorkoutBottomSheet(
    state: WorkoutState,
    onEvent: (WorkoutEvent) -> Unit,
    onDismiss: () -> Unit,
) {

    BetterYouBottomSheet(onDismissRequest = onDismiss) {
        // Name plan
        TBCAppTextField(
            value = state.workoutName,
            onValueChange = {
                onEvent(WorkoutEvent.OnExerciseNameChange(it))
            },
            placeholder = stringResource(R.string.workout_plan_name),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Search field
        TBCAppSearchField(
            value = state.searchQuery,
            onValueChange = {
                onEvent(WorkoutEvent.OnSearchChange(it))
            },
            placeholder = stringResource(R.string.search_exercise),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Workouts
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(state.filteredWorkouts, key = { workout -> workout.id }) { workout ->

                ExerciseSelectionRow(
                    workout = workout,
                    isSelected = workout.isSelected,
                    onSelect = { checked ->
                        onEvent.invoke(WorkoutEvent.OnSelectExercise(workout))
                    }
                )
            }
        }

        TBCAppButton(
            text = "Create Workout",
            onClick = {
                val selectedExercises = state.filteredWorkouts.filter { it.isSelected }

                onEvent.invoke(
                    WorkoutEvent.OnSaveWorkout(
                        title = state.workoutName,
                        exercises = selectedExercises
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            type = AppButtonType.Accent,
            enabled = state.filteredWorkouts.any { it.isSelected }
        )
    }
}