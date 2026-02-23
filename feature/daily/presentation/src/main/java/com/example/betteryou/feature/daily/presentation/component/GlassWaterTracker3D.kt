package com.example.betteryou.feature.daily.presentation.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.LocalTBCColors
import com.example.betteryou.core_ui.theme.LocalTBCTypography
import com.example.betteryou.core_ui.theme.Spacer
import kotlin.math.sin

@Composable
fun GlassWaterTracker3D(
    currentWater: Float,
    waterGoal: Double,
    onWaterChange: (Float) -> Unit,
) {
    val safeWaterGoal = if (waterGoal > 0) waterGoal.toFloat() else 1f
    val targetFill = (currentWater / safeWaterGoal).coerceIn(0f, 1f)

    val percentage = (targetFill * 100).toInt()

    val animatedPercentage by animateIntAsState(
        targetValue = percentage, animationSpec = tween(1000), label = "waterPercent"
    )

    val animatedFill by animateFloatAsState(
        targetValue = if (waterGoal > 0) (currentWater / waterGoal.toFloat()) else 0f,
        animationSpec = tween(1000)
    )

    val hydrationMessage = when {
        percentage == 0 -> stringResource(R.string.start_drinking)
        percentage < 40 -> stringResource(R.string.keep_going)
        percentage < 70 -> stringResource(R.string.good_progress)
        percentage < 90 -> stringResource(R.string.almost_done)
        percentage < 100 -> stringResource(R.string.so_close)
        else -> stringResource(R.string.goal_reached)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.water_intake),
            style = LocalTBCTypography.current.bodyLarge,
            color = LocalTBCColors.current.onBackground
        )
        Spacer(Modifier.height(Spacer.spacing16))

        Row(verticalAlignment = Alignment.CenterVertically) {

            IconButton(onClick = { onWaterChange((currentWater - 0.5f).coerceAtLeast(0f)) }) {
                Icon(
                    painterResource(R.drawable.minus_svgrepo_com),
                    contentDescription = null,
                    tint = LocalTBCColors.current.onBackground
                )
            }

            Box(
                modifier = Modifier.size(150.dp, 180.dp), contentAlignment = Alignment.BottomCenter
            ) {
                val infiniteTransition = rememberInfiniteTransition()
                val wavePhase by infiniteTransition.animateFloat(
                    0f, (2 * Math.PI).toFloat(), animationSpec = infiniteRepeatable(
                        animation = tween(5000, easing = LinearEasing)
                    )
                )
                val horizontalShift by infiniteTransition.animateFloat(
                    initialValue = -10f, targetValue = 10f, animationSpec = infiniteRepeatable(
                        animation = tween(8000, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                )

                Canvas(modifier = Modifier.matchParentSize()) {
                    val w = size.width
                    val h = size.height
                    val waterHeight = h * animatedFill

                    val topLeftX = 0.1f * w
                    val topRightX = 0.9f * w
                    val bottomLeftX = 0.2f * w
                    val bottomRightX = 0.8f * w
                    val topOvalHeight = 20f

                    val glassPath = Path().apply {
                        moveTo(topLeftX, 0f)
                        cubicTo(topLeftX, 0f, w / 2, -topOvalHeight, topRightX, 0f)
                        lineTo(bottomRightX, h)
                        lineTo(bottomLeftX, h)
                        close()
                    }

                    drawPath(glassPath, color = Color.Gray.copy(alpha = 0.4f), style = Stroke(3f))

                    if (animatedFill > 0f) {
                        val steps = 60
                        val maxAmplitude = (waterHeight * 0.05f).coerceAtMost(12f)
                        val waveFrequency = 2f

                        val waterPath = Path().apply {
                            val currentTopLeftX =
                                topLeftX + (bottomLeftX - topLeftX) * (1 - animatedFill)
                            val currentTopRightX =
                                topRightX - (topRightX - bottomRightX) * (1 - animatedFill)
                            moveTo(currentTopLeftX, h - waterHeight)
                            for (i in 0..steps) {
                                val fraction = i / steps.toFloat()
                                val x =
                                    currentTopLeftX + (currentTopRightX - currentTopLeftX) * fraction
                                val surfaceWave =
                                    (sin(fraction * waveFrequency * 2 * Math.PI + wavePhase) * maxAmplitude).toFloat()
                                val sideOffset =
                                    (sin(fraction * Math.PI + wavePhase) * horizontalShift * 0.5f).toFloat()
                                val y = (h - waterHeight + surfaceWave).coerceIn(
                                    h - waterHeight - maxAmplitude, h
                                )
                                lineTo(x + sideOffset, y)
                            }
                            lineTo(bottomRightX, h)
                            lineTo(bottomLeftX, h)
                            close()
                        }

                        drawPath(
                            path = waterPath, brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFF4FC3F7), Color(0xFF29B6F6)),
                                startY = h - waterHeight - maxAmplitude,
                                endY = h
                            )
                        )
                    }

                    drawPath(
                        path = glassPath, brush = Brush.verticalGradient(
                            colors = listOf(Color.White.copy(alpha = 0.3f), Color.Transparent),
                            startY = 0f,
                            endY = h * 0.5f
                        )
                    )
                }
            }

            IconButton(onClick = { onWaterChange((currentWater + 0.5f).coerceAtMost(waterGoal.toFloat())) }) {
                Icon(
                    painterResource(R.drawable.plus_svgrepo_com_2),
                    contentDescription = null,
                    tint = LocalTBCColors.current.onBackground
                )
            }
        }

        Spacer(Modifier.height(Spacer.spacing16))

        Text(
            text = stringResource(
                id = R.string.water_progress, currentWater, waterGoal
            ),
            color = LocalTBCColors.current.onBackground,
            style = LocalTBCTypography.current.bodyLargest
        )

        Text(
            text = stringResource(
                id = R.string.hydration_message, animatedPercentage
            ),
            style = LocalTBCTypography.current.bodyLargest,
            color = LocalTBCColors.current.onBackground
        )

        Spacer(Modifier.height(Spacer.spacing8))

        Text(
            text = hydrationMessage,
            style = LocalTBCTypography.current.bodyLarge,
            color = LocalTBCColors.current.onBackground.copy(alpha = 0.7f)
        )
    }
}
