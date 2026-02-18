package com.betteryou.workout.presentation.screen.details.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.core_ui.theme.Spacer

@Composable
fun SetRow(
    reps: String,
    weight: String,
    onRepsChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onDelete: () -> Unit,
) {
    var localReps by remember(reps) { mutableStateOf(reps) }
    var localWeight by remember(weight) { mutableStateOf(weight) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacer.spacing12)
    ) {

        // Reps input
        Column(modifier = Modifier.weight(1f)) {

            Text(
                "Reps",
                color = TBCTheme.colors.textSecondary,
                style = TBCTheme.typography.bodySmallest
            )

            Spacer(modifier = Modifier.height(Spacer.spacing4))

            DetailsInputField(
                value = localReps,
                onValueChange = { newValue ->
                    localReps = newValue
                    onRepsChange(newValue)
                }
            )
        }

        // Weight input
        Column(modifier = Modifier.weight(1f)) {

            Text(
                "Weight",
                color = TBCTheme.colors.textSecondary,
                style = TBCTheme.typography.bodySmallest
            )

            Spacer(modifier = Modifier.height(Spacer.spacing4))

            DetailsInputField(
                value = localWeight,
                onValueChange = { newValue ->
                    localWeight = newValue
                    onWeightChange(newValue)
                }
            )
        }

        IconButton(
            onClick = onDelete,
            modifier = Modifier
                .align(Alignment.Bottom)
                .padding(bottom = 4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_garbage),
                contentDescription = null,
                tint = TBCTheme.colors.textSecondary
            )
        }
    }
}
