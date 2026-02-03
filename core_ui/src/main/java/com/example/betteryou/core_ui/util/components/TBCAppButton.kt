package com.example.betteryou.core_ui.util.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.local_theme.LocalTBCColors
import com.example.betteryou.core_ui.local_theme.LocalTBCTypography

enum class AppButtonType {
    Primary,
    Secondary,
    Outlined
}

@Composable
fun TBCAppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    type: AppButtonType = AppButtonType.Primary
) {
    val colors = LocalTBCColors.current
    val typography = LocalTBCTypography.current

    val backgroundColor = when (type) {
        AppButtonType.Primary -> colors.primary
        AppButtonType.Secondary,
        AppButtonType.Outlined -> colors.surface
    }

    val textColor = when (type) {
        AppButtonType.Primary -> colors.onPrimary
        else -> colors.textPrimary
    }

    val border = when (type) {
        AppButtonType.Primary -> null
        else -> BorderStroke(1.dp, colors.border)
    }

    Box(
        modifier = modifier
            .height(52.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .then(
                if (border != null)
                    Modifier.border(border, RoundedCornerShape(16.dp))
                else Modifier
            )
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = typography.labelLarge,
            color = textColor
        )
    }
}
