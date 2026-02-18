package com.betteryou.workout.presentation.screen.workout.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.betteryou.workout.presentation.model.WorkoutUI
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.core_ui.theme.Spacer

@Composable
fun WorkoutCard(
    workout: WorkoutUI,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = TBCTheme.colors.card
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // Title
                Text(
                    text = workout.title,
                    style = TBCTheme.typography.headlineMedium,
                    color = TBCTheme.colors.textPrimary
                )

                Spacer(modifier = Modifier.height(Spacer.spacing8))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.icon_exercise),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = TBCTheme.colors.textSecondary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${workout.exercises.size} Exercises",
                        style = TBCTheme.typography.bodyMedium,
                        color = TBCTheme.colors.textSecondary
                    )
                }
            }

            Icon(
                painter = painterResource(R.drawable.icon_arrow_right),
                contentDescription = "Open details",
                tint = TBCTheme.colors.textSecondary
            )
        }
    }
}