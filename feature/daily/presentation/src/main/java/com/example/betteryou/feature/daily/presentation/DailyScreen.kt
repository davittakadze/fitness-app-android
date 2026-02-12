package com.example.betteryou.feature.daily.presentation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.betteryou.core_res.R
import com.example.betteryou.core_ui.TBCTheme
import com.example.betteryou.core_ui.local_theme.LocalTBCColors
import com.example.betteryou.core_ui.local_theme.LocalTBCTypography
import com.example.betteryou.core_ui.util.Radius
import com.example.betteryou.core_ui.util.Spacer
import kotlin.math.absoluteValue
import kotlin.math.sin


//gasatania stringebi

@Composable
fun DailyScreen(
    viewModel: DailyViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
    //    viewModel.onEvent(DailyEvent.LoadUserNutrition)
    }
    DailyScreenContent(state, viewModel::onEvent)
}

@Composable
fun DailyScreenContent(
    state: DailyState,
    onEvent: (DailyEvent) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    Column(
        Modifier
            .fillMaxSize()
            .background(LocalTBCColors.current.background)
            .padding(
                vertical = 48.dp
            )
    ) {
        Spacer(Modifier.height(32.dp))
        Text(
            text =
                "Daily intake",
            style = LocalTBCTypography.current.headlineLarge,
            color = LocalTBCColors.current.onBackground,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(Modifier.height(32.dp))
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 32.dp),
            pageSpacing = 16.dp
        )
        { page ->
            val pageOffset = (pagerState.currentPage - page + pagerState.currentPageOffsetFraction)
                .coerceIn(-1f, 1f)

            when (page) {
                0 -> PageContent(pageOffset = pageOffset) {
                    MacroCircleChart(
                        consumedCalories = state.consumedCalories,
                        protein = state.protein,
                        fat = state.fat,
                        carbs = state.carbs,
                        totalCaloriesGoal = state.totalCaloriesGoal,
                        totalProteinGoal = state.totalProteinGoal,
                        totalFatGoal = state.totalFatGoal,
                        totalCarbsGoal = state.totalCarbsGoal
                    )
                }

                1 -> PageContent(pageOffset = pageOffset) {
                    GlassWaterTracker3D(
                        currentWater = state.currentWater,
                        waterGoal = state.waterGoal,
                        onWaterChange = { newWater -> onEvent(DailyEvent.ChangeWater(newWater)) }
                    )

                }
            }
        }
        Spacer(Modifier.height(Spacer.spacing32))
    }
}

@Composable
fun PageContent(
    pageOffset: Float,
    content: @Composable ColumnScope.() -> Unit,
) {
    val shadowElevationDp = 8.dp
    val shadowElevationPx = with(LocalDensity.current) { shadowElevationDp.toPx() }
    val minHeightDp = 350.dp
    val maxHeightDp = 400.dp
    val heightDp =
        maxHeightDp - (maxHeightDp - minHeightDp) * pageOffset.absoluteValue

    Box(
        modifier = Modifier
            .height(heightDp)
            .graphicsLayer {
                shadowElevation = shadowElevationPx
                shape = Radius.radius16
                clip = true
            }
            .clip(Radius.radius16)
            .background(LocalTBCColors.current.background)
            .border(2.dp, LocalTBCColors.current.border, Radius.radius16)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}

@Composable
fun MacroCircleChart(
    consumedCalories: Int,
    protein: Int,
    fat: Int,
    carbs: Int,
    totalProteinGoal: Int,
    totalFatGoal: Int,
    totalCarbsGoal: Int,
    totalCaloriesGoal: Int,
) {
    // --- Safe fractions ---
    val fillFraction = (consumedCalories.toFloat() / totalCaloriesGoal.coerceAtLeast(1).toFloat())
        .coerceIn(0f, 1f)

    val totalSweep = 360f * fillFraction

    val proteinCalories = protein.toFloat() * 4f
    val fatCalories = fat.toFloat() * 9f
    val carbsCalories = carbs.toFloat() * 4f

    val macroCaloriesSum = (proteinCalories + fatCalories + carbsCalories)
        .coerceAtLeast(1f)

    val proteinFraction = proteinCalories / macroCaloriesSum
    val fatFraction = fatCalories / macroCaloriesSum
    val carbsFraction = carbsCalories / macroCaloriesSum

    // --- Animate angles as Float ---
    val proteinAngle by animateFloatAsState(targetValue = totalSweep * proteinFraction)
    val fatAngle by animateFloatAsState(targetValue = totalSweep * fatFraction)
    val carbsAngle by animateFloatAsState(targetValue = totalSweep * carbsFraction)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Calories intake",
            style = LocalTBCTypography.current.bodyLarge,
            color = LocalTBCColors.current.onBackground
        )

        Spacer(modifier = Modifier.height(Spacer.spacing16))

        Box(
            modifier = Modifier.size(200.dp),
            contentAlignment = Alignment.Center
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
                    text = "${consumedCalories} kkal",
                    style = LocalTBCTypography.current.headlineLarge,
                    color = LocalTBCColors.current.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${totalCaloriesGoal} kkal",
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
                text = "Protein",
                goal = totalProteinGoal,
                current = protein,
            )
            Spacer(Modifier.width(Spacer.spacing16))
            VerticalDivider(
                Modifier
                    .fillMaxHeight()
                    .width(1.5.dp),
                color = LocalTBCColors.current.border
            )
            Spacer(Modifier.width(Spacer.spacing16))
            InfoItem(
                color = LocalTBCColors.current.fat,
                text = "Fat",
                goal = totalFatGoal,
                current = fat
            )
            Spacer(Modifier.width(Spacer.spacing16))
            VerticalDivider(
                Modifier
                    .fillMaxHeight()
                    .width(1.5.dp),
                color = LocalTBCColors.current.border
            )
            Spacer(Modifier.width(Spacer.spacing16))
            InfoItem(
                color = LocalTBCColors.current.carbs,
                text = "Carbs",
                goal = totalCarbsGoal,
                current = carbs
            )
        }
    }
}

@Composable
private fun InfoItem(
    color: Color,
    text: String,
    goal: Int,
    current: Int,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Box(
                Modifier
                    .size(16.dp)
                    .clip(Radius.radius32)
                    .background(color)
            )
            Spacer(Modifier.width(Spacer.spacing4))
            Text(
                text = text,
                style = LocalTBCTypography.current.bodyLarge,
                color = LocalTBCColors.current.onBackground
            )
        }
        Spacer(Modifier.height(Spacer.spacing8))
        Text(
            text = "${current}g",
            style = LocalTBCTypography.current.bodyLarge,
            color = LocalTBCColors.current.onBackground
        )
        Spacer(Modifier.height(Spacer.spacing8))
        Text(
            text = "${goal}g",
            style = LocalTBCTypography.current.bodyMedium,
            color = LocalTBCColors.current.onBackground
        )
    }
}

@Composable
fun GlassWaterTracker3D(
    currentWater: Float,
    waterGoal: Float = 3f,
    onWaterChange: (Float) -> Unit,
) {
    val fillFraction = (currentWater / waterGoal).coerceIn(0f, 1f)
    val animatedFill by animateFloatAsState(
        targetValue = fillFraction,
        animationSpec = tween(durationMillis = 600)
    )

    val percentage = (fillFraction * 100).toInt()
    val animatedPercentage by animateIntAsState(
        targetValue = percentage,
        animationSpec = tween(600)
    )

    val hydrationMessage = when {
        percentage == 0 -> "Start drinking 💧"
        percentage < 40 -> "Keep going!"
        percentage < 70 -> "Good progress!"
        percentage < 90 -> "Almost done!"
        percentage < 100 -> "So close!"
        else -> "Goal reached! 🎉"
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Water intake",
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
                modifier = Modifier.size(150.dp, 180.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                val infiniteTransition = rememberInfiniteTransition()
                val wavePhase by infiniteTransition.animateFloat(
                    0f,
                    (2 * Math.PI).toFloat(),
                    animationSpec = infiniteRepeatable(
                        animation = tween(5000, easing = LinearEasing)
                    )
                )
                val horizontalShift by infiniteTransition.animateFloat(
                    initialValue = -10f,
                    targetValue = 10f,
                    animationSpec = infiniteRepeatable(
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
                            val currentTopLeftX = topLeftX + (bottomLeftX - topLeftX) * (1 - animatedFill)
                            val currentTopRightX = topRightX - (topRightX - bottomRightX) * (1 - animatedFill)
                            moveTo(currentTopLeftX, h - waterHeight)
                            for (i in 0..steps) {
                                val fraction = i / steps.toFloat()
                                val x = currentTopLeftX + (currentTopRightX - currentTopLeftX) * fraction
                                val surfaceWave = (sin(fraction * waveFrequency * 2 * Math.PI + wavePhase) * maxAmplitude).toFloat()
                                val sideOffset = (sin(fraction * Math.PI + wavePhase) * horizontalShift * 0.5f).toFloat()
                                val y = (h - waterHeight + surfaceWave).coerceIn(h - waterHeight - maxAmplitude, h)
                                lineTo(x + sideOffset, y)
                            }
                            lineTo(bottomRightX, h)
                            lineTo(bottomLeftX, h)
                            close()
                        }

                        drawPath(
                            path = waterPath,
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFF4FC3F7), Color(0xFF29B6F6)),
                                startY = h - waterHeight - maxAmplitude,
                                endY = h
                            )
                        )
                    }

                    drawPath(
                        path = glassPath,
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.White.copy(alpha = 0.3f), Color.Transparent),
                            startY = 0f,
                            endY = h * 0.5f
                        )
                    )
                }
            }

            IconButton(onClick = { onWaterChange((currentWater + 0.5f).coerceAtMost(waterGoal)) }) {
                Icon(
                    painterResource(R.drawable.plus_svgrepo_com_2),
                    contentDescription = null,
                    tint = LocalTBCColors.current.onBackground
                )
            }
        }

        Spacer(Modifier.height(Spacer.spacing16))

        Text(
            text = "${"%.2f".format(currentWater)}L / ${waterGoal}L",
            color = LocalTBCColors.current.onBackground,
            style = LocalTBCTypography.current.bodyLargest
        )

        Text(
            text = "$animatedPercentage% hydrated",
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

@Preview
@Composable
fun DailyScreenPreview() {
    TBCTheme { DailyScreenContent(DailyState()) {} }
}