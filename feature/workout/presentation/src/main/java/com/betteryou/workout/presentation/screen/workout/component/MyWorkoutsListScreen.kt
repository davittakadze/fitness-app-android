package com.betteryou.workout.presentation.screen.workout.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
            Text(
                text = "No custom workouts yet.\nClick '+' to create one!",
                textAlign = TextAlign.Center,
                style = TBCTheme.typography.bodyMedium,
                color = TBCTheme.colors.textSecondary
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(workouts, key = { it.id }) { workout ->
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = { value ->
                        if (value == SwipeToDismissBoxValue.EndToStart) {
                            onEvent(WorkoutEvent.DeleteWorkout(workout.id))
                            true
                        } else false
                    }
                )

                SwipeToDismissBox(
                    state = dismissState,
                    enableDismissFromStartToEnd = false,
                    backgroundContent = {
                        val isVisible = dismissState.targetValue == SwipeToDismissBoxValue.EndToStart

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                                .clip(Radius.radius16)
                                .background(if (isVisible) Color.Red.copy(alpha = 0.2f) else Color.Transparent),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(80.dp)
                                    .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
                                    .background(Color.Red)
                                    .clickable { onEvent(WorkoutEvent.DeleteWorkout(workout.id)) },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_garbage),
                                    contentDescription = "Delete",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                ) {
                    WorkoutCard(
                        workout = workout,
                        onClick = { onWorkoutClick(workout.id) }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(Spacer.spacing64))
            }

        }
    }
}