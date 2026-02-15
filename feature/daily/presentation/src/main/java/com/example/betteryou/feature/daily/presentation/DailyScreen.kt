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
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.betteryou.core_res.R
import com.example.betteryou.core_ui.TBCTheme
import com.example.betteryou.core_ui.local_theme.LocalTBCColors
import com.example.betteryou.core_ui.local_theme.LocalTBCTypography
import com.example.betteryou.core_ui.util.Radius
import com.example.betteryou.core_ui.util.Spacer
import com.example.betteryou.core_ui.util.components.AppAsyncImage
import com.example.betteryou.core_ui.util.components.AppButtonType
import com.example.betteryou.core_ui.util.components.TBCAppButton
import com.example.betteryou.core_ui.util.components.TBCAppTextField
import com.example.betteryou.feature.daily.domain.model.UserDailyProduct
import com.example.betteryou.feature.daily.presentation.model.ProductUi
import com.example.betteryou.feature.daily.presentation.model.UserDailyProductUi
import kotlin.math.absoluteValue
import kotlin.math.sin

//gasatania stringebi

@Composable
fun DailyScreen(
    viewModel: DailyViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    DailyScreenContent(state, viewModel::onEvent)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyScreenContent(
    state: DailyState,
    onEvent: (DailyEvent) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    val pagerState = rememberPagerState(
        initialPage = state.currentPage,
        pageCount = { 2 }
    )

    LaunchedEffect(pagerState.currentPage) {
        onEvent(DailyEvent.ChangePage(pagerState.currentPage))
    }

    LaunchedEffect(state.currentPage) {
        if (pagerState.currentPage != state.currentPage) {
            pagerState.scrollToPage(state.currentPage)
        }
    }

    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(LocalTBCColors.current.background)
                .padding(vertical = 64.dp)
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }
        ) {
            item {
                Text(
                    text = "Daily intake",
                    style = TBCTheme.typography.headlineLarge,
                    color = TBCTheme.colors.onBackground,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }

            item { Spacer(Modifier.height(Spacer.spacing16)) }

            item {
                CustomDropdown(
                    state.products,
                    selectedItem = null,
                    onItemSelected = { product ->
                        onEvent(DailyEvent.OpenBottomSheet(product))
                    }
                )
            }

            item { Spacer(Modifier.height(Spacer.spacing16)) }

            item {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    key = { it },
                    contentPadding = PaddingValues(horizontal = 32.dp),
                    pageSpacing = 16.dp,
                    beyondViewportPageCount = 1
                ) { page ->
                    val pageOffset =
                        (pagerState.currentPage - page + pagerState.currentPageOffsetFraction)
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
                                onWaterChange = { newWater ->
                                    onEvent(DailyEvent.ChangeWater(newWater))
                                }
                            )
                        }
                    }
                }
            }

            item { Spacer(Modifier.height(Spacer.spacing32)) }

            items(state.consumedProducts) { consumedProduct ->
                ConsumedProductItem(consumedProduct)
            }

            item { Spacer(Modifier.height(Spacer.spacing32)) }
        }

        if (state.isBottomSheetOpen && state.selectedProduct != null) {
            ModalBottomSheet(
                onDismissRequest = { onEvent(DailyEvent.CloseBottomSheet) },
                containerColor = TBCTheme.colors.background
            ) {
                BottomSheet(
                    state.selectedProduct,
                    onClose = { onEvent(DailyEvent.CloseBottomSheet) },
                    addProductQuantity = { quantity, product ->
                        onEvent(
                            DailyEvent.AddProductQuantity(
                                quantity = quantity,
                                product = product
                            )
                        )
                    }
                )
            }
        }
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
    val fillFraction = (consumedCalories.toFloat() / totalCaloriesGoal.coerceAtLeast(1).toFloat())
        .coerceIn(0f, 1f)

    val proteinCalories = protein.toFloat() * 4f
    val fatCalories = fat.toFloat() * 9f
    val carbsCalories = carbs.toFloat() * 4f

    val macroCaloriesSum = (proteinCalories + fatCalories + carbsCalories)
        .coerceAtLeast(1f)

    val proteinFraction = proteinCalories / macroCaloriesSum
    val fatFraction = fatCalories / macroCaloriesSum
    val carbsFraction = carbsCalories / macroCaloriesSum

    val animatedTotalSweep by animateFloatAsState(
        targetValue = 360f * fillFraction,
        animationSpec = tween(1000)
    )

    val proteinAngle by animateFloatAsState(targetValue = animatedTotalSweep * proteinFraction)
    val fatAngle by animateFloatAsState(targetValue = animatedTotalSweep * fatFraction)
    val carbsAngle by animateFloatAsState(targetValue = animatedTotalSweep * carbsFraction)


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
                    text = "$consumedCalories kkal",
                    style = LocalTBCTypography.current.headlineLarge,
                    color = LocalTBCColors.current.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$totalCaloriesGoal kkal",
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
    waterGoal: Double,
    onWaterChange: (Float) -> Unit,
) {
    val safeWaterGoal = if (waterGoal > 0) waterGoal.toFloat() else 1f
    val targetFill = (currentWater / safeWaterGoal).coerceIn(0f, 1f)

    val percentage = (targetFill * 100).toInt()

    val animatedPercentage by animateIntAsState(
        targetValue = percentage,
        animationSpec = tween(1000),
        label = "waterPercent"
    )

    val animatedFill by animateFloatAsState(
        targetValue = if (waterGoal > 0) (currentWater / waterGoal.toFloat()) else 0f,
        animationSpec = tween(1000)
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
                                    h - waterHeight - maxAmplitude,
                                    h
                                )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdown(
    items: List<ProductUi>,
    selectedItem: ProductUi?,
    onItemSelected: (ProductUi) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    val filteredItems = remember(query, items) {
        if (query.isEmpty()) items
        else items.filter { it.name.contains(query, ignoreCase = true) }
    }

    val textFieldValue = query.ifEmpty {
        selectedItem?.name.orEmpty()
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .clip(Radius.radius12)
    ) {

        TBCAppTextField(
            value = textFieldValue,
            onValueChange = {
                query = it
                expanded = true
            },
            placeholder = "Select product",
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                query = ""
            },
            modifier = Modifier
                .background(TBCTheme.colors.background, shape = Radius.radius12)
                .clip(Radius.radius12)
        ) {
            filteredItems.forEach { item ->
                DropdownMenuItem(
                    text = { CustomDropdownItem(item) },
                    onClick = {
                        onItemSelected(item)
                        query = ""
                        expanded = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun CustomDropdownItem(item: ProductUi) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AppAsyncImage(
            model = item.photo,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
        )

        Spacer(modifier = Modifier.width(Spacer.spacing12))

        Text(
            text = item.name,
            style = MaterialTheme.typography.bodyMedium,
            color = TBCTheme.colors.onBackground
        )

        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(R.drawable.right_arrow_svgrepo_com),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = TBCTheme.colors.onBackground
        )
    }
}

@Composable
fun BottomSheet(
    item: ProductUi,
    onClose: () -> Unit,
    addProductQuantity: (quantity: Int, product: ProductUi) -> Unit,
) {
    var quantity by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    Column(
        Modifier
            .fillMaxHeight(0.9f)
            .verticalScroll(rememberScrollState())
            .imePadding()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }) {
        AppAsyncImage(
            model = item.photo,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
        Spacer(modifier = Modifier.height(Spacer.spacing12))
        Column {
            Text(
                text = item.name,
                style = TBCTheme.typography.headlineLarge,
                color = TBCTheme.colors.onBackground,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(Spacer.spacing16))
            Text(
                text = item.description,
                style = TBCTheme.typography.bodyLarge,
                color = TBCTheme.colors.onBackground,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(Spacer.spacing32))
            Text(
                text = "Per 100g",
                style = TBCTheme.typography.headlineLarge,
                color = TBCTheme.colors.onBackground,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(Spacer.spacing16))
            Text(
                text = "Calories:    ${item.calories}",
                style = TBCTheme.typography.bodyLarge,
                color = TBCTheme.colors.onBackground,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(Spacer.spacing8))
            Text(
                text = "Protein:    ${item.protein}",
                style = TBCTheme.typography.bodyLarge,
                color = TBCTheme.colors.onBackground,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(Spacer.spacing8))
            Text(
                text = "Fat:    ${item.fat}",
                style = TBCTheme.typography.bodyLarge,
                color = TBCTheme.colors.onBackground,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(Spacer.spacing8))
            Text(
                text = "Carb:    ${item.carbs}",
                style = TBCTheme.typography.bodyLarge,
                color = TBCTheme.colors.onBackground,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
        Spacer(modifier = Modifier.height(Spacer.spacing16))
        Row(Modifier.padding(horizontal = 24.dp)) {
            Spacer(Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                TBCAppTextField(
                    value = quantity,
                    onValueChange = { input ->
                        if (input.all { it.isDigit() }) {
                            quantity = input
                        }
                    },
                    placeholder = "100",
                    keyboardType = KeyboardType.Number,
                    numbersOnly = true,
                    modifier = Modifier
                        .size(width = 82.dp, height = 52.dp)
                        .align(Alignment.CenterVertically)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "g.",
                    style = TBCTheme.typography.bodyLarge,
                    color = TBCTheme.colors.onBackground,
                    modifier = Modifier.padding(end = 24.dp, top = 24.dp, bottom = 24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(Spacer.spacing16))
        TBCAppButton(
            text = "Add",
            onClick = {
                val amount: Int = quantity.toIntOrNull() ?: 0
                if (amount > 0) {
                    addProductQuantity(amount, item)
                }
                onClose()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(52.dp),
            type = AppButtonType.Outlined
        )
        Spacer(modifier = Modifier.height(Spacer.spacing16))
    }
}

@Preview
@Composable
fun DailyScreenPreview() {
    TBCTheme {
        //    DailyScreenContent(DailyState()) {}
        ConsumedProductItem(
            UserDailyProductUi(
                userId = "denfienf",
                productId = 34,
                name = "Milk",
                photo = "",
                calories = 930,
                protein = 10.0,
                carbs = 10.0,
                fat = 10.0,
                description = "Milk",
                quantity = 100,
                date = 37484939
            ),
            {}
        )
    }
}

@Composable
fun ConsumedProductItem(item: UserDailyProductUi,onRemove:(userId:String,productId:Int)->Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .height(210.dp)
            .border(shape = Radius.radius12, width = 1.dp, color = TBCTheme.colors.border)
            .clip(Radius.radius12)
            .padding(24.dp)
    ) {
        Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            AppAsyncImage(
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape),
                model = item.photo
            )
            Spacer(Modifier.width(Spacer.spacing16))
            Column(Modifier
                .wrapContentWidth()
                .fillMaxHeight()) {
                Text(
                    text = item.name,
                    style = TBCTheme.typography.bodyLargest,
                    color = TBCTheme.colors.onBackground
                )
                Spacer(Modifier.height(Spacer.spacing16))
                Text(
                    text = "${item.quantity}g",
                    style = TBCTheme.typography.bodyLarge,
                    color = TBCTheme.colors.onBackground
                )
                Spacer(Modifier.height(Spacer.spacing8))
                Text(
                    text = "${item.calories} kkal",
                    style = TBCTheme.typography.bodyLarge,
                    color = TBCTheme.colors.onBackground
                )
                Spacer(Modifier.height(Spacer.spacing8))
                Text(
                    text = "protein: ${item.protein} g.",
                    style = TBCTheme.typography.bodyLarge,
                    color = TBCTheme.colors.onBackground
                )
                Spacer(Modifier.height(Spacer.spacing8))
                Text(
                    text = "fats: ${item.fat} g.",
                    style = TBCTheme.typography.bodyLarge,
                    color = TBCTheme.colors.onBackground
                )
                Spacer(Modifier.height(Spacer.spacing8))
                Text(
                    text = "carbs: ${item.carbs} g.",
                    style = TBCTheme.typography.bodyLarge,
                    color = TBCTheme.colors.onBackground
                )
            }
            Spacer(Modifier.width(Spacer.spacing4))
            IconButton(
                onClick = {

                },
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Top)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.minus_svgrepo_com),
                    contentDescription = null,
                    tint = TBCTheme.colors.onBackground
                )
            }
        }
    }
}