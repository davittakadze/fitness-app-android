package com.example.betteryou.feature.profile.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.theme.LocalTBCColors
import com.example.betteryou.core_ui.theme.LocalTBCTypography

@Composable
fun SexItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val background =
        if (selected) LocalTBCColors.current.onBackground
        else Color.Transparent

    val textColor =
        if (selected) LocalTBCColors.current.background
        else LocalTBCColors.current.onBackground

    val borderColor =
        if (selected) Color.Transparent
        else LocalTBCColors.current.border

    Box(
        modifier = modifier
            .height(44.dp)
            .background(background, CircleShape)
            .border(1.dp, borderColor, CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = LocalTBCTypography.current.bodyLarge,
            color = textColor
        )
    }
}