package com.betteryou.workout.presentation.screen.workout.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.betteryou.workout.presentation.model.ExerciseUI
import com.example.betteryou.core_res.R
import com.example.betteryou.core_ui.TBCTheme
import com.example.betteryou.core_ui.util.Radius
import com.example.betteryou.core_ui.util.components.TBCAppAsyncImage

@Composable
fun ExerciseSelectionRow(
    workout: ExerciseUI,
    isSelected: Boolean,
    onSelect: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = TBCTheme.colors

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(Radius.radius12)
            .background(
                if (isSelected) colors.accent.copy(alpha = 0.1f)
                else Color.Transparent
            )
            .clickable { onSelect(!isSelected) }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Image
        Box(
            modifier = Modifier
                .fillMaxWidth(0.35f)
                .aspectRatio(1.7f)
                .background(Color.Transparent, Radius.radius8),
            contentAlignment = Alignment.Center
        ) {
            TBCAppAsyncImage(
                img = workout.imageUrl,
                placeholder = R.drawable.icon_workout_screen,
                contentDescription = null,
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Name and Category
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = workout.name,
                style = TBCTheme.typography.bodyLarge,
                color = if (isSelected) colors.accent else colors.textPrimary
            )
            Text(
                text = workout.category,
                style = TBCTheme.typography.bodyMedium,
                color = colors.textSecondary
            )
        }

        Box(
            modifier = Modifier
                .size(24.dp)
                .background(Color.Transparent, CircleShape)
                .border(2.dp, if (isSelected) colors.accent else colors.border, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    tint = colors.accent,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}