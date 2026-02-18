package com.betteryou.feature.history.presentation.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.betteryou.feature.history.presentation.extension.toLocalDate
import com.betteryou.feature.history.presentation.screen.model.HistoryUI
import com.example.betteryou.core_res.R
import com.example.betteryou.core_ui.TBCTheme
import com.example.betteryou.core_ui.util.Radius
import com.example.betteryou.core_ui.util.Spacer.spacing16
import com.example.betteryou.core_ui.util.Spacer.spacing8
import com.example.betteryou.util.formatToString

@Composable
fun HistoryCard(
    item: HistoryUI,
) {
    var isExpanded by remember { mutableStateOf(false) }

    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 90f else 0f,
        label = "Rotation"
    )

    Card(
        onClick = { isExpanded = !isExpanded },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        shape = Radius.radius16,
        colors = CardDefaults.cardColors(containerColor = TBCTheme.colors.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(58.dp)
                    .background(
                        color = TBCTheme.colors.background,
                        shape = Radius.radius12
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.calendar_svgrepo_com),
                    contentDescription = null,
                    tint = TBCTheme.colors.accent,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(spacing8))

            Column(modifier = Modifier
                .padding(12.dp)
                .weight(1f)
            ) {
                Text(
                    text = item.workoutTitle,
                    style = TBCTheme.typography.bodyLargest,
                    color = TBCTheme.colors.textPrimary
                )
                Spacer(modifier = Modifier.height(spacing8))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.icon_exercise),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = TBCTheme.colors.textSecondary
                    )
                    Spacer(modifier = Modifier.width(spacing8))

                    Text(
                        text = item.timestamp.toLocalDate().formatToString(),
                        style = TBCTheme.typography.bodySmallest,
                        color = TBCTheme.colors.textSecondary
                    )
                }

                if (isExpanded) {

                    Spacer(modifier = Modifier.height(spacing16))
                    HorizontalDivider(color = TBCTheme.colors.textSecondary)
                    Spacer(modifier = Modifier.height(spacing8))

                    item.exercises.forEach { detail ->

                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            Text(
                                text = detail.exercise.name,
                                style = TBCTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = TBCTheme.colors.accent
                            )

                            detail.sets.forEachIndexed { index, set ->
                                val reps = set.reps.ifBlank { "0" }
                                val weight = set.weight.ifBlank { "0" }


                                Row(
                                    modifier = Modifier.padding(start = 12.dp, top = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${index + 1}. ",
                                        style = TBCTheme.typography.bodySmallest,
                                        color = TBCTheme.colors.textSecondary
                                    )
                                    Text(
                                        text = "$reps Reps x $weight kg",
                                        style = TBCTheme.typography.bodySmallest,
                                        color = TBCTheme.colors.textPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Icon(
                painter = painterResource(R.drawable.right_arrow_svgrepo_com),
                contentDescription = "Details",
                tint = TBCTheme.colors.textSecondary,
                modifier = Modifier
                    .size(20.dp)
                    .rotate(rotationState)
            )
        }
    }
}
