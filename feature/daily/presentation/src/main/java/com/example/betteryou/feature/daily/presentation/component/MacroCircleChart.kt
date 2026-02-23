package com.example.betteryou.feature.daily.presentation.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.LocalTBCColors
import com.example.betteryou.core_ui.theme.LocalTBCTypography
import com.example.betteryou.core_ui.theme.Spacer

@Composable
fun MacroCircleChart(
    consumedCalories: Double,
    protein: Double,
    fat: Double,
    carbs: Double,
    totalProteinGoal: Double,
    totalFatGoal: Double,
    totalCarbsGoal: Double,
    totalCaloriesGoal: Double,
) {
    val fillFraction =
        (consumedCalories.toFloat() / totalCaloriesGoal.coerceAtLeast(1.0).toFloat()).coerceIn(
            0f, 1f
        )

    val proteinCalories = protein.toFloat() * 4f
    val fatCalories = fat.toFloat() * 9f
    val carbsCalories = carbs.toFloat() * 4f

    val macroCaloriesSum = (proteinCalories + fatCalories + carbsCalories).coerceAtLeast(1f)

    val proteinFraction = proteinCalories / macroCaloriesSum
    val fatFraction = fatCalories / macroCaloriesSum
    val carbsFraction = carbsCalories / macroCaloriesSum

    val animatedTotalSweep by animateFloatAsState(
        targetValue = 360f * fillFraction, animationSpec = tween(1000)
    )

    val proteinAngle by animateFloatAsState(targetValue = animatedTotalSweep * proteinFraction)
    val fatAngle by animateFloatAsState(targetValue = animatedTotalSweep * fatFraction)
    val carbsAngle by animateFloatAsState(targetValue = animatedTotalSweep * carbsFraction)


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.calories_intake_string),
            style = LocalTBCTypography.current.bodyLarge,
            color = LocalTBCColors.current.onBackground
        )

        Spacer(modifier = Modifier.height(Spacer.spacing16))

        Box(
            modifier = Modifier.size(200.dp), contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {

                // Background circle
                drawArc(
                    color = Color.LightGray,
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 50f, cap = StrokeCap.Round)
                )

                var startAngle = -90f

                // Protein
                drawArc(
                    color = Color(0xFFFF7043),
                    startAngle = startAngle,
                    sweepAngle = proteinAngle,
                    useCenter = false,
                    style = Stroke(width = 50f, cap = StrokeCap.Round)
                )
                startAngle += proteinAngle

                // Fat
                drawArc(
                    color = Color(0xFFFFD54F),
                    startAngle = startAngle,
                    sweepAngle = fatAngle,
                    useCenter = false,
                    style = Stroke(width = 50f, cap = StrokeCap.Round)
                )
                startAngle += fatAngle

                // Carbs
                drawArc(
                    color = Color(0xFF81D4FA),
                    startAngle = startAngle,
                    sweepAngle = carbsAngle,
                    useCenter = false,
                    style = Stroke(width = 50f, cap = StrokeCap.Round)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.calories, consumedCalories),
                    style = LocalTBCTypography.current.headlineLarge,
                    color = LocalTBCColors.current.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.calories, totalCaloriesGoal),
                    style = LocalTBCTypography.current.bodyLargest,
                    color = LocalTBCColors.current.onBackground
                )
            }
        }

        Spacer(modifier = Modifier.height(Spacer.spacing32))

        Row(
            Modifier
                .wrapContentHeight()
                .padding(horizontal = 4.dp, vertical = 6.dp)
        ) {
            InfoItem(
                color = LocalTBCColors.current.protein,
                text = stringResource(R.string.protein),
                goal = totalProteinGoal,
                current = protein,
            )
            Spacer(Modifier.width(Spacer.spacing16))
            VerticalDivider(
                Modifier
                    .fillMaxHeight()
                    .width(1.5.dp), color = LocalTBCColors.current.border
            )
            Spacer(Modifier.width(Spacer.spacing16))
            InfoItem(
                color = LocalTBCColors.current.fat,
                text = stringResource(R.string.fat),
                goal = totalFatGoal,
                current = fat
            )
            Spacer(Modifier.width(Spacer.spacing16))
            VerticalDivider(
                Modifier
                    .fillMaxHeight()
                    .width(1.5.dp), color = LocalTBCColors.current.border
            )
            Spacer(Modifier.width(Spacer.spacing16))
            InfoItem(
                color = LocalTBCColors.current.carbs,
                text = stringResource(R.string.carb),
                goal = totalCarbsGoal,
                current = carbs
            )
        }
    }
}