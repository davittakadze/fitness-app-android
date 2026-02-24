package com.example.betteryou.feature.daily.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.theme.LocalTBCColors
import com.example.betteryou.core_ui.theme.Radius
import kotlin.math.absoluteValue

@Composable
fun PageContent(
    pageOffset: Float,
    content: @Composable ColumnScope.() -> Unit,
) {
    val shadowElevationDp = 8.dp
    val shadowElevationPx = with(LocalDensity.current) { shadowElevationDp.toPx() }
    val minHeightDp = 370.dp
    val maxHeightDp = 420.dp
    val heightDp = maxHeightDp - (maxHeightDp - minHeightDp) * pageOffset.absoluteValue

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
            .padding(16.dp)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}