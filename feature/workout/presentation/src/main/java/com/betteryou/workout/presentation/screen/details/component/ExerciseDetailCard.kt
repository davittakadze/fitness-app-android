package com.betteryou.workout.presentation.screen.details.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.betteryou.workout.presentation.model.WorkoutExerciseUI
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.core_ui.theme.Spacer
import com.example.betteryou.core_ui.components.TBCAppAsyncImage

@Composable
fun ExerciseDetailCard(
    workoutExercise: WorkoutExerciseUI,
    onAddSet: (String) -> Unit,
    onRemoveSet: (Long) -> Unit,
    onUpdateSet: (Long, String, String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = TBCTheme.colors.card)
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = workoutExercise.exercise.name,
                style = TBCTheme.typography.headlineMedium,
                color = TBCTheme.colors.textPrimary,
                modifier = Modifier.align(Alignment.Start)
            )

            TBCAppAsyncImage(
                img = workoutExercise.exercise.imageUrl,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .aspectRatio(1.2f)
                    .padding(vertical = 16.dp),
                placeholder = R.drawable.icon_workout_screen,
            )

            workoutExercise.sets.forEachIndexed { index, set ->

                SetRow(
                    reps = set.reps,
                    weight = set.weight,
                    onRepsChange = { newReps -> onUpdateSet(set.setId, newReps, set.weight) },
                    onWeightChange = { newWeight -> onUpdateSet(set.setId, set.reps, newWeight) },
                    onDelete = { onRemoveSet(set.setId) }
                )

                Spacer(modifier = Modifier.height(Spacer.spacing8))
            }

            TextButton(
                onClick = { onAddSet(workoutExercise.exercise.id) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = null,
                    tint = TBCTheme.colors.accent
                )

                Spacer(modifier = Modifier.width(Spacer.spacing8))

                Text("Add Set", color = TBCTheme.colors.accent)
            }
        }
    }
}
