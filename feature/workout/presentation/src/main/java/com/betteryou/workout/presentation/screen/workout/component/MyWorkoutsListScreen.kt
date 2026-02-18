package com.betteryou.workout.presentation.screen.workout.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.betteryou.workout.presentation.screen.workout.WorkoutEvent
import com.betteryou.workout.presentation.model.WorkoutUI
import com.example.betteryou.core_ui.TBCTheme
import com.example.betteryou.core_ui.util.Radius
import com.example.betteryou.core_ui.util.Spacer
import com.example.betteryou.core_res.R
import com.example.betteryou.core_ui.util.components.SwipeToDeleteContainer
import com.example.betteryou.core_ui.util.components.TBCAppScreenPlaceholder

@Composable
fun MyWorkoutsListScreen(
    workouts: List<WorkoutUI>,
    onWorkoutClick: (String) -> Unit,
    onEvent: (WorkoutEvent) -> Unit
) {
    if (workouts.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            TBCAppScreenPlaceholder(
                modifier = Modifier
                    .padding(bottom = 100.dp)
                    .fillMaxWidth(),
                icon = R.drawable.icon_workout_screen,
                primaryText = "No custom workouts yet.",
                secondaryText = "Click '+' to create one!"
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(workouts, key = { it.id }) { workout ->
                SwipeToDeleteContainer(
                    onDelete = { onEvent(WorkoutEvent.DeleteWorkout(workout.id)) }
                ) {
                    WorkoutCard(
                        workout = workout,
                        onClick = { onWorkoutClick(workout.id) }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}